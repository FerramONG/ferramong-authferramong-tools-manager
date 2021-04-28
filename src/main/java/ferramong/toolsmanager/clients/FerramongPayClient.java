package ferramong.toolsmanager.clients;

import ferramong.toolsmanager.config.FerramongConfig;
import ferramong.toolsmanager.dto.Payment;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@Component
@Slf4j
public class FerramongPayClient {
    private final FerramongConfig ferramongConfig;
    private final WebClient webClient;

    public FerramongPayClient(FerramongConfig ferramongConfig) {
        this.ferramongConfig = ferramongConfig;
        this.webClient = WebClient.create(ferramongConfig.getPayBaseUrl());
    }

    public void chargeCreditools(Payment payment) {
        final String path = "/pay/creditools";

        final Duration timeout = Duration.of(ferramongConfig.getPayTimeoutInSeconds(), SECONDS);
        log.info("chargeCreditools: requestBody={}",
                ReflectionToStringBuilder.toString(payment, ToStringStyle.JSON_STYLE));
        webClient.post()
                .uri(path)
                .body(BodyInserters.fromValue(payment))
                .retrieve()
                .toBodilessEntity()
                .block(timeout);
    }

    public void addCreditools(Payment payment) {
        final String path = "/reward";

        final Duration timeout = Duration.of(ferramongConfig.getPayTimeoutInSeconds(), SECONDS);
        webClient.post()
                .uri(path)
                .body(BodyInserters.fromValue(payment))
                .retrieve()
                .toBodilessEntity()
                .block(timeout);
    }
}
