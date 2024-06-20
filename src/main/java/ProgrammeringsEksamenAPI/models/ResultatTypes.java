package ProgrammeringsEksamenAPI.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

public class ResultatTypes {
    @Entity
    @Getter
    @Setter
    public class TimeResultat extends Resultat {
        private double tid; // Time value
    }

    @Entity
    @Getter
    @Setter
    public class DistanceResultat extends Resultat {
        private double afstand; // Distance value
    }

    @Entity
    @Getter
    @Setter
    public class PointResultat extends Resultat {
        private int point; // Point value
    }
}
