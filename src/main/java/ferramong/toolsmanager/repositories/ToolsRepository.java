package ferramong.toolsmanager.repositories;

import ferramong.toolsmanager.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToolsRepository extends JpaRepository<Tool, Integer> {

    List<Tool> findAllByOwnerId(int ownerId);
    Optional<Tool> findByIdAndOwnerId(int toolId, int ownerId);
    List<Tool> deleteByOwnerId(int ownerId);
    Optional<Tool> deleteByIdAndOwnerId(int toolId, int ownerId);

    @Query("select t from Tool t where lower(t.name) like concat(lower(:toolName), '%')")
    List<Tool> findByNameStartingWith(String toolName);

}
