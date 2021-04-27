package ferramong.toolsmanager.clients;

import ferramong.toolsmanager.config.FerramongConfig;
import ferramong.toolsmanager.dto.Dweller;
import ferramong.toolsmanager.exceptions.DwellerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

import static java.lang.String.format;
import static java.time.temporal.ChronoUnit.SECONDS;

@Component
@Slf4j
public class FerramongAuthClient {
    private final FerramongConfig ferramongConfig;
    private final WebClient webClient;

    public FerramongAuthClient(FerramongConfig ferramongConfig) {
        this.ferramongConfig = ferramongConfig;
        this.webClient = WebClient.create(ferramongConfig.getAuthBaseUrl());
    }

    public Dweller getDwellerById(int dwellerId) throws DwellerNotFoundException {
        final String path = format("/accountManager/getDweller/id/%s", dwellerId);
        log.trace("Calling GET {}{}", ferramongConfig.getAuthBaseUrl(), path);

        final Duration timeout = Duration.of(ferramongConfig.getAuthTimeoutInSeconds(), SECONDS);
        return webClient.get()
                .uri(path)
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        response -> response.createException().ofType(DwellerNotFoundException.class)
                ).bodyToMono(Dweller.class)
                .blockOptional(timeout)
                .orElseThrow(() -> new DwellerNotFoundException(dwellerId));
    }

    public Dweller getDwellerByName(String name) throws DwellerNotFoundException {
        final String path = format("/accountManager/getDweller/name/%s", name);
        log.trace("Calling GET {}{}", ferramongConfig.getAuthBaseUrl(), path);

        final Duration timeout = Duration.of(ferramongConfig.getAuthTimeoutInSeconds(), SECONDS);
        return webClient.get()
                .uri(path)
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        response -> response.createException().ofType(DwellerNotFoundException.class)
                ).bodyToMono(Dweller.class)
                .blockOptional(timeout)
                .orElseThrow(() -> new DwellerNotFoundException(name));
    }
}
