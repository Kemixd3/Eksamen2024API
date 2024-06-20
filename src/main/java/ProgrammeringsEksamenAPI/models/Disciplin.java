package ProgrammeringsEksamenAPI.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "discipliner")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Disciplin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String navn;
    private String resultattype;

    @OneToMany(mappedBy = "disciplin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resultat> resultater = new ArrayList<>();


    // Its late - Lazy but very easy to access resultat records
        public void addResult(Resultat resultat) {
            resultat.setDisciplin(this);
            this.resultater.add(resultat);
        }
}

