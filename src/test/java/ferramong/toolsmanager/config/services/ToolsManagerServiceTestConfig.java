package ferramong.toolsmanager.config.services;

import ferramong.toolsmanager.repositories.ToolsRepository;
import ferramong.toolsmanager.services.ToolsService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ToolsManagerServiceTestConfig {
    @MockBean private ToolsRepository toolsRepository;

    @Bean
    public ToolsService toolsManagerService() {
        return new ToolsService(toolsRepository);
    }
}
