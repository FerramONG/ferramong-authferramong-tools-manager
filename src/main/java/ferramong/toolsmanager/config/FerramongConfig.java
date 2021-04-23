package ferramong.toolsmanager.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class FerramongConfig {

    @Value("${ferramong.tools-manager.base-url}")
    private String toolsManagerBaseUrl;

    @Value("${ferramong.auth.base-url}")
    private String authBaseUrl;
}
