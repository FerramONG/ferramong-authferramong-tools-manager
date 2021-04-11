package ferramong.toolsmanager.repositories;

import ferramong.toolsmanager.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToolsRepository extends JpaRepository<Tool, Integer> {

    Optional<Tool> findByIdAndOwnerId(int toolId, int ownerId);
    Optional<Tool> deleteByIdAndOwnerId(int toolId, int ownerId);

    List<Tool> findAllByOwnerId(int ownerId);
    List<Tool> findByNameStartsWithIgnoreCase(String toolName);
    List<Tool> deleteAllByOwnerId(int ownerId);

}
