package ProgrammeringsEksamenAPI.services.resultat;

import ProgrammeringsEksamenAPI.models.Deltager;
import ProgrammeringsEksamenAPI.models.Disciplin;
import ProgrammeringsEksamenAPI.models.Resultat;
import ProgrammeringsEksamenAPI.models.resultatModels.PointResultat;
import ProgrammeringsEksamenAPI.models.resultatModels.TimeDistanceResultat;
import ProgrammeringsEksamenAPI.models.resultatModels.TimeResultat;
import ProgrammeringsEksamenAPI.repository.DeltagerRepository;
import ProgrammeringsEksamenAPI.repository.DisciplinRepository;
import dto.resultat.ResultatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ResultatServiceImpl implements ResultatServiceInterface {

    @Autowired
    private DisciplinRepository disciplinRepository;

    @Autowired
    private DeltagerRepository deltagerRepository;

    @Override
    public ResponseEntity<?> addPointsResultat(Long disciplinId, Long deltagerId, ResultatRequest request) {
        // Assuming timeTaken is not needed for addPointsResultat, pass null or a default value
        Float timeTaken = null; // or provide a default value if needed

        return addResultat(disciplinId, deltagerId, request.getPoints(), request.getDato(), timeTaken, "point", null);
    }


    @Override
    public ResponseEntity<?> addTimeResultat(Long disciplinId, Long deltagerId, ResultatRequest request) {
        // Assuming points is not needed for addTimeResultat, pass null or a default value
        Integer points = null; // or provide a default value if needed

        return addResultat(disciplinId, deltagerId, points, request.getDato(), request.getTimeTaken(), "time", null);
    }

    @Override
    public ResponseEntity<?> addDistanceResultat(Long disciplinId, Long deltagerId, ResultatRequest request) {
        return addResultat(disciplinId, deltagerId, null, request.getDato(), request.getTimeTaken(), "time_distance", request.getDistance());
    }

    private ResponseEntity<?> addResultat(Long disciplinId, Long deltagerId, Integer points, LocalDate dato, Float timeTaken, String resultatType, Double distance) {
        Optional<Disciplin> disciplinOptional = disciplinRepository.findById(disciplinId);
        Optional<Deltager> deltagerOptional = deltagerRepository.findById(deltagerId);

        if (disciplinOptional.isEmpty() || deltagerOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Disciplin disciplin = disciplinOptional.get();
        Deltager deltager = deltagerOptional.get();

        Resultat resultat;
        switch (resultatType) {
            case "point":
                PointResultat pointsResultat = new PointResultat();
                pointsResultat.setPoints(points);
                resultat = pointsResultat;
                break;
            case "time":
                TimeResultat timeResultat = new TimeResultat();
                timeResultat.setTimeTaken(timeTaken);
                resultat = timeResultat;
                break;
            case "time_distance":
                TimeDistanceResultat distanceResultat = new TimeDistanceResultat();
                distanceResultat.setTimeTaken(timeTaken);
                distanceResultat.setDistance(distance);
                resultat = distanceResultat;
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid resultat type");
        }

        resultat.setDato(dato);
        resultat.setDisciplin(disciplin);
        resultat.setDeltager(deltager);

        disciplin.addResult(resultat);
        disciplinRepository.save(disciplin);

        return ResponseEntity.status(HttpStatus.CREATED).body(resultat);
    }
}

