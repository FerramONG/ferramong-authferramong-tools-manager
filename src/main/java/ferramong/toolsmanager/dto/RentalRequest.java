package ferramong.toolsmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalRequest {
    @NotNull
    private int ownerDwellerId;
    @NotNull
    private int renterDwellerId;
    @NotNull
    private int toolId;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;
}
