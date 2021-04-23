package ferramong.toolsmanager.services;

import ferramong.toolsmanager.dto.RentalRequest;
import ferramong.toolsmanager.entities.Rental;
import ferramong.toolsmanager.entities.Tool;
import ferramong.toolsmanager.exceptions.RentalNotFoundException;
import ferramong.toolsmanager.exceptions.ToolNotAvailableException;
import ferramong.toolsmanager.exceptions.ToolNotFoundException;
import ferramong.toolsmanager.repositories.RentalsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class RentalsService {
    private final RentalsRepository repository;
    private final ToolsService toolsService;

    public List<Rental> getAllRentedTools(int renterDwellerId) {
        return repository.findAllByRenterId(renterDwellerId);
    }

    public List<Rental> getAllRentedTools(@NotNull String toolName) {
        return repository.findAllByToolNameStartsWithIgnoreCase(toolName);
    }

    public List<Rental> getAllRentedOrOwnedTools(int dwellerId) {
        return repository.findAllByRenterIdOrToolOwnerId(dwellerId, dwellerId);
    }

    @Transactional
    public Rental rentTool(@NotNull RentalRequest rentalRequest)
            throws ToolNotFoundException, ToolNotAvailableException {
        // TODO: Validate dweller IDs.
        var toolToRent = toolsService.getTool(rentalRequest.getToolId(), rentalRequest.getOwnerDwellerId());
        if (isAvailableForRental(toolToRent, rentalRequest.getReturnDate())) {
            // TODO: Charge dweller to rent tool.

            return repository.save(
                    Rental.builder()
                            .renterId(rentalRequest.getRenterDwellerId())
                            .tool(toolToRent)
                            .rentFrom(LocalDate.now())
                            .rentUntil(null)
                            .expectedReturnDate(rentalRequest.getReturnDate())
                            .build()
            );
        }

        throw new ToolNotAvailableException(toolToRent.getId(), toolToRent.getAvailableUntil());
    }

    @Transactional
    public Rental returnTool(int toolId, int ownerDwellerId) throws RentalNotFoundException {
        var rentalEntity = repository.findByToolIdAndToolOwnerId(toolId, ownerDwellerId);
        if (rentalEntity.isPresent()) {
            var rentalToReturn = rentalEntity.get();
            rentalToReturn.setRentUntil(LocalDate.now());

            // TODO: If delayed, check if payment has been done. Use pay/debit API.
            // TODO: If returned earlier, return creditools to renter. Use pay/credit API.
            // TODO: Pay tool owner for rented days.

            return repository.save(rentalToReturn);
        }

        throw new RentalNotFoundException(toolId);
    }

    @Transactional
    public Rental cancelRental(int toolId, int ownerDwellerId) throws RentalNotFoundException {
        var rentalEntity = repository.findByToolIdAndToolOwnerId(toolId, ownerDwellerId);
        if (rentalEntity.isPresent()) {
            // TODO: Return creditools to renter.
            var rentalToCancel = rentalEntity.get();
            repository.delete(rentalToCancel);

            return rentalEntity.get();
        }

        throw new RentalNotFoundException(toolId);
    }

    private boolean isAvailableForRental(@NotNull Tool toolToRent, @NotNull LocalDate rentUntil) {
        var rentalEntity = repository.findByToolIdAndToolOwnerId(toolToRent.getId(), toolToRent.getOwnerId());
        return rentalEntity.isEmpty() && !rentUntil.isAfter(toolToRent.getAvailableUntil());
    }
}
