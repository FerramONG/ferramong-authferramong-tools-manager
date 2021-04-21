package ferramong.toolsmanager.config.controllers;

import ferramong.toolsmanager.services.ToolsService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class ToolsManagerControllerTestConfig {
    @MockBean private ToolsService toolsService;
}
