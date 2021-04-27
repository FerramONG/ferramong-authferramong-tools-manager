package ferramong.toolsmanager.repositories;

import ferramong.toolsmanager.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToolsRepository extends JpaRepository<Tool, Integer> {

    Optional<Tool> findByIdAndOwnerId(int toolId, int ownerId);

    List<Tool> findAllByOwnerId(int ownerId);
    List<Tool> deleteAllByOwnerId(int ownerId);
    List<Tool> deleteAllByOwnerIdAndIdNotIn(int ownerId, List<Integer> excludedToolIds);

    @Query(
            "select t from Tool t " +
            "where upper(t.name) like concat(upper(:name), '%') " +
                "and t.id not in ( select r.tool.id from Rental r )"
    )
    List<Tool> findAllAvailableByNameStartsWith(@Param("name") String name);

    @Query("select t from Tool t where t.ownerId = :ownerId and t.id not in ( select r.tool.id from Rental r )")
    List<Tool> findAllAvailableByOwnerId(@Param("ownerId") int ownerId);

}
