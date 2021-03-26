package ferramong.toolsmanager.config;

import ferramong.toolsmanager.entities.Dweller;
import ferramong.toolsmanager.entities.Tool;
import ferramong.toolsmanager.repositories.DwellersRepository;
import ferramong.toolsmanager.repositories.ToolsRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class BootstrapConfig implements CommandLineRunner {

    private final ToolsRepository toolsRepository;
    private final DwellersRepository dwellersRepository;

    @Override
    public void run(String... args) {
        initializeToolsInDatabase();
    }

    public void initializeToolsInDatabase() {
        var johnDoe = createDweller("John Doe");
        var janeDoe = createDweller("Jane Doe");
        persistDwellers(johnDoe, janeDoe);

        for (int i = 0; i < 10; i++) {
            var tool = createTool("Hammer " + i, johnDoe);
            persistTools(tool);
        }

        for (int i = 0; i < 10; i++) {
            var tool = createTool("Saw " + i, janeDoe);
            persistTools(tool);
        }
    }

    private Dweller createDweller(String name) {
        return Dweller.builder()
                .cpf("000-000-000.00")
                .name(name)
                .build();
    }

    private Tool createTool(String name, Dweller dweller) {
        return Tool.builder()
                .name(name)
                .category("Category")
                .description("What is this tool for?")
                .instructions("How to use this tool?")
                .price(100.0)
                .availableFrom(LocalDate.now())
                .availableUntil(LocalDate.now().plusWeeks(1L))
                .owner(dweller)
                .build();
    }

    private void persistDwellers(Dweller... dwellers) {
        for (var dweller : dwellers) {
            if (dwellersRepository.findByName(dweller.getName()).isEmpty()) {
                dwellersRepository.saveAndFlush(dweller);
            }
        }
    }

    private void persistTools(Tool... tools) {
        for (var tool : tools) {
            if (toolsRepository.findByName(tool.getName()).isEmpty()) {
                toolsRepository.saveAndFlush(tool);
            }
        }
    }
}
