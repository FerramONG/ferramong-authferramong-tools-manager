package ferramong.toolsmanager.repositories;

import ferramong.toolsmanager.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalsRepository extends JpaRepository<Rental, Integer> {

    Optional<Rental> findByToolIdAndToolOwnerId(int toolId, int ownerDwellerId);

    List<Rental> findAllByRenterId(int renterDwellerId);
    List<Rental> findAllByToolOwnerId(int ownerDwellerId);
    List<Rental> findAllByRenterIdOrToolOwnerId(int renterDwellerId, int ownerDwellerId);
    List<Rental> findAllByToolNameStartsWithIgnoreCase(String toolName);
}
