package ferramong.toolsmanager.repositories;

import ferramong.toolsmanager.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalsRepository extends JpaRepository<Rental, Integer> { }
