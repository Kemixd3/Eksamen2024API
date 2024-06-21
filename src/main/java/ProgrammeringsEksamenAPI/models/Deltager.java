package ProgrammeringsEksamenAPI.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "deltagere")
public class Deltager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String navn;
    private String køn;
    private int alder;
    private String klub;

    @OneToMany(mappedBy = "deltager", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Resultat> resultater;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "deltager_disciplin",
            joinColumns = @JoinColumn(name = "deltager_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplin_id")
    )
    private List<Disciplin> discipliner = new ArrayList<>();


    @Transient
    public String getAlderGroup() {
        if (alder >= 6 && alder <= 9) {
            return "Børn (6-9)";
        } else if (alder >= 10 && alder <= 13) {
            return "Unge (10-13)";
        } else if (alder >= 14 && alder <= 22) {
            return "Junior (14-22)";
        } else if (alder >= 23 && alder <= 40) {
            return "Voksne (23-40)";
        } else if (alder >= 41) {
            return "Senior (41+)";
        } else {
            return "Ukendt";
        }
    }


}
