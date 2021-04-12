package ferramong.toolsmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rentals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "renter_id")
    private int renterId;
    @OneToOne
    @JoinColumn(name = "tool_id", referencedColumnName = "id")
    private Tool tool;

    @Column(name = "rent_from")
    private LocalDate rentFrom;
    @Column(name = "rent_until")
    private LocalDate rentUntil;
}
