package ferramong.toolsmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    @Column(name = "password", length = 256, nullable = false)
    private String password;
    @Column(name = "secret_question", length = 1024, nullable = false)
    private String secretQuestion;
    @Column(name = "secret_answer", length = 1024, nullable = false)
    private String secretAnswer;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Tool> tools;
}
