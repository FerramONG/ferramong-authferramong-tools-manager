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
    private Double price;

    private Integer ownerId;
    private Integer renterId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate availableFrom;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate availableUntil;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate rentFrom;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate rentUntil;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectedReturnDate;

}
