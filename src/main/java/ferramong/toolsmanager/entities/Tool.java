package ferramong.toolsmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tools")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 512, nullable = false)
    private String name;
    @Column(name = "category", length = 512)
    private String category;
    @Column(name = "description", length = 4096)
    private String description;
    @Column(name = "instructions", length = 4096)
    private String instructions;
    @Column(name = "price")
    private double price;

    @Column(name = "available_from")
    private LocalDate availableFrom;
    @Column(name = "available_until")
    private LocalDate availableUntil;

    @Column(name = "owner_id")
    private int ownerId;
}
