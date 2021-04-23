package ferramong.toolsmanager.clients;

import ferramong.toolsmanager.config.FerramongConfig;
import ferramong.toolsmanager.dto.Dweller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Component
@Slf4j
public class FerramongAuthClient {
    private final FerramongConfig ferramongConfig;
    private final WebClient webClient;

    public FerramongAuthClient(FerramongConfig ferramongConfig) {
        this.ferramongConfig = ferramongConfig;
        this.webClient = WebClient.create(ferramongConfig.getAuthBaseUrl());
    }

    public Mono<Dweller> getDwellerByName(String name) {
        final String path = format("/accountManager/getDweller/name/%s", name);
        log.trace("Calling GET {}{}", ferramongConfig.getAuthBaseUrl(), path);

        return webClient.get()
                .uri(path)
                .retrieve()
                .bodyToMono(Dweller.class);
    }
}
