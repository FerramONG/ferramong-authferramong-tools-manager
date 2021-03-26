package ferramong.toolsmanager.repositories;

import ferramong.toolsmanager.entities.Dweller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DwellersRepository extends JpaRepository<Dweller, Integer> {

    List<Dweller> findByName(String name);
}
