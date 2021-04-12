package ferramong.toolsmanager.config.controllers;

import ferramong.toolsmanager.services.ToolsManagerService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class ToolsManagerControllerTestConfig {
    @MockBean private ToolsManagerService toolsManagerService;
}
