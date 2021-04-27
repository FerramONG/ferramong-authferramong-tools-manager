package ferramong.toolsmanager.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class FerramongConfig {
    @Value("${ferramong.auth.base-url}")
    private String authBaseUrl;

    @Value("${ferramong.auth.timeout}")
    private Integer authTimeoutInSeconds;

    @Value("${ferramong.pay.base-url}")
    private String payBaseUrl;

    @Value("${ferramong.pay.timeout}")
    private Integer payTimeoutInSeconds;
}
