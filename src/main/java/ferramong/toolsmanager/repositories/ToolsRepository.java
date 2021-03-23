package ferramong.toolsmanager.repositories;

import ferramong.toolsmanager.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolsRepository extends JpaRepository<Tool, Integer> {

    @Query(
            "select t from Tool t " +
            "where t.name = :toolName " +
                "and ( t.owner.name = :dwellerName or t.renter.name = :dwellerName )"
    )
    List<Tool> findToolsByNameAndAssociatedDweller(
            @Param("toolName") String toolName,
            @Param("dwellerName") String dwellerName
    );
}
