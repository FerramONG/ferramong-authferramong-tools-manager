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
public class RentedTool {
    private Tool tool;
    private Integer renterDwellerId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate rentFrom;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate rentUntil;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectedReturnDate;
}
