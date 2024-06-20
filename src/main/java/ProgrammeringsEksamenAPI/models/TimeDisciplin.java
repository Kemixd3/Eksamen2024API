/*
package ProgrammeringsEksamenAPI.models;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import ProgrammeringsEksamenAPI.services.Disciplin.Disciplin;
import java.util.List;

@Entity
public class TimeDisciplin implements Disciplin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String navn;

    @OneToMany(mappedBy = "disciplin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resultat> resultater;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getNavn() {
        return navn;
    }

    @Override
    public String getResultattype() {
        return "time";
    }

    @Override
    public List<Resultat> getResultater() {
        return resultater;
    }

    @Override
    public void setResultater(List<Resultat> resultater) {
        this.resultater = resultater;
    }
}
*/
