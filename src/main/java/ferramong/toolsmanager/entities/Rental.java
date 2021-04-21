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

    @Column(name = "renter_id", nullable = false)
    private int renterId;
    @OneToOne
    @JoinColumn(name = "tool_id", referencedColumnName = "id", nullable = false)
    private Tool tool;

    @Column(name = "rent_from", nullable = false)
    private LocalDate rentFrom;
    @Column(name = "rent_until")
    private LocalDate rentUntil;
    @Column(name = "expected_return_date", nullable = false)
    private LocalDate expectedReturnDate;
}
