package ferramong.toolsmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(NON_NULL)
public class Tool {
    private int id;
    private String name;
    private String category;
    private String description;
    private String instructions;
    private double price;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate availableFrom;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate availableUntil;

    private int ownerId;
}
