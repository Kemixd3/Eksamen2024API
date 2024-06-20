package ProgrammeringsEksamenAPI.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "resultat_type")
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "resultater")
public abstract class Resultat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deltager_id", nullable = false)
    private Deltager deltager;

    @ManyToOne
    @JoinColumn(name = "disciplin_id", nullable = false)
    private Disciplin disciplin;

    // Constructors, getters, setters
}
