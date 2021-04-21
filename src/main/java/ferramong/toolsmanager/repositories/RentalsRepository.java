package ferramong.toolsmanager.repositories;

import ferramong.toolsmanager.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalsRepository extends JpaRepository<Rental, Integer> {
    List<Rental> findAllByRenterId(int renterDwellerId);
    Optional<Rental> findByToolIdAndToolOwnerId(int toolId, int ownerDwellerId);
    Optional<Rental> deleteByToolIdAndToolOwnerId(int toolId, int ownerDwellerId);
}
