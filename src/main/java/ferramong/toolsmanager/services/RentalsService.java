package ferramong.toolsmanager.services;

import ferramong.toolsmanager.clients.FerramongAuthClient;
import ferramong.toolsmanager.clients.FerramongPayClient;
import ferramong.toolsmanager.dto.Payment;
import ferramong.toolsmanager.dto.RentalRequest;
import ferramong.toolsmanager.entities.Rental;
import ferramong.toolsmanager.entities.Tool;
import ferramong.toolsmanager.exceptions.DwellerNotFoundException;
import ferramong.toolsmanager.exceptions.RentalNotFoundException;
import ferramong.toolsmanager.exceptions.ToolNotAvailableException;
import ferramong.toolsmanager.exceptions.ToolNotFoundException;
import ferramong.toolsmanager.repositories.RentalsRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Service
@NoArgsConstructor
public class RentalsService {
    @Autowired private RentalsRepository repository;
    @Autowired private ToolsService toolsService;
    @Autowired private FerramongAuthClient authClient;
    @Autowired private FerramongPayClient payClient;

    public Rental getRentedTool(int toolId, int ownerDwellerId) throws RentalNotFoundException {
        return repository.findByToolIdAndToolOwnerId(toolId, ownerDwellerId)
                .orElseThrow(() -> new RentalNotFoundException(toolId));
    }

    public List<Rental> getAllRentedToolsByOwner(int ownerDwellerId) {
        return repository.findAllByToolOwnerId(ownerDwellerId);
    }

    public List<Rental> getAllRentedToolsByRenter(int renterDwellerId) {
        return repository.findAllByRenterId(renterDwellerId);
    }

    public List<Rental> getAllRentedToolsByName(@NotNull String toolName) {
        return repository.findAllByToolNameStartsWithIgnoreCase(toolName);
    }

    public List<Rental> getAllRentedOrOwnedTools(int dwellerId) {
        return repository.findAllByRenterIdOrToolOwnerId(dwellerId, dwellerId);
    }

    @Transactional
    public Rental rentTool(@NotNull RentalRequest rentalRequest)
            throws ToolNotFoundException, ToolNotAvailableException, DwellerNotFoundException {
        authClient.getDwellerById(rentalRequest.getRenterDwellerId());

        var toolToRent = toolsService.getTool(rentalRequest.getToolId(), rentalRequest.getOwnerDwellerId());
        if (isAvailableForRental(toolToRent, rentalRequest.getReturnDate())) {
//            payClient.chargeCreditools(new Payment(rentalRequest.getRenterDwellerId(), toolToRent.getPrice()));

            return repository.save(
                    Rental.builder()
                            .tool(toolToRent)
                            .renterId(rentalRequest.getRenterDwellerId())
                            .rentFrom(LocalDate.now())
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

//            chargeDelayedDaysIfAny(rentalToReturn);
//            payToolOwner(rentalToReturn);

            return repository.save(rentalToReturn);
        }

        throw new RentalNotFoundException(toolId);
    }

    private boolean isAvailableForRental(@NotNull Tool toolToRent, @NotNull LocalDate rentUntil) {
        var rentalEntity = repository.findByToolIdAndToolOwnerId(toolToRent.getId(), toolToRent.getOwnerId());
        return rentalEntity.isEmpty() && !rentUntil.isAfter(toolToRent.getAvailableUntil());
    }

    private void chargeDelayedDaysIfAny(Rental rentalToReturn) {
        final int delayedDays = LocalDate.now().compareTo(rentalToReturn.getExpectedReturnDate());
        if (delayedDays > 0) {
            final double priceToCharge = rentalToReturn.getTool().getPrice() * delayedDays;
            payClient.chargeCreditools(new Payment(rentalToReturn.getRenterId(), priceToCharge));
        }
    }

    private void payToolOwner(Rental rentalToReturn) {
        final LocalDate rentFrom = rentalToReturn.getRentFrom();
        final LocalDate rentUntil = rentalToReturn.getRentUntil();

        final int rentedDays = rentUntil.compareTo(rentFrom);
        final double priceToPay = rentalToReturn.getTool().getPrice() * rentedDays;
        payClient.addCreditools(new Payment(rentalToReturn.getTool().getOwnerId(), priceToPay));
    }
}
