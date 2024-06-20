package ProgrammeringsEksamenAPI.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
@Table(name = "deltagere")
public class Deltager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String navn;
    private String køn; // Assuming this is meant to be 'køn' (gender)
    private int alder;
    private String klub;


    //OLD
   // @OneToMany(mappedBy = "deltager", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Resultat> resultater = new ArrayList<>();


    @OneToMany(mappedBy = "deltager")
    private List<Resultat> resultater;


    @ManyToMany
    @JoinTable(
            name = "deltager_disciplin",
            joinColumns = @JoinColumn(name = "deltager_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplin_id")
    )
    private List<Disciplin> discipliner = new ArrayList<>();

    // Constructors, getters, setters
}
