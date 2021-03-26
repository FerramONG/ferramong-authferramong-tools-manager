package ferramong.toolsmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "dwellers")
@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class Dweller {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 512, nullable = false)
    private String name;
    @Column(name = "cpf", length = 128, nullable = false)
    private String cpf;
}
