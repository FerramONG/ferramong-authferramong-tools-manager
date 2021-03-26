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

    @OneToOne
    @JoinColumn(name = "renter_id", referencedColumnName = "id")
    private Dweller renter;
    @OneToOne
    @JoinColumn(name = "tool_id", referencedColumnName = "id")
    private Tool tool;

    @Column(name = "rentFrom")
    private LocalDate rentFrom;
    @Column(name = "rentUntil")
    private LocalDate rentUntil;
}
