package ferramong.toolsmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToolRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String category;
    @NotBlank
    private String description;
    @NotBlank
    private String instructions;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate availableFrom;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate availableUntil;

    private double price;
}
