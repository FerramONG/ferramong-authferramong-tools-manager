package ferramong.toolsmanager.repositories;

import ferramong.toolsmanager.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolsRepository extends JpaRepository<Tool, Integer> {

    List<Tool> findByName(String toolName);

    @Query("select t from Tool t where lower(t.name) like concat(lower(:toolName), '%')")
    List<Tool> findByNameStartingWith(String toolName);
}
