package ProgrammeringsEksamenAPI.services.resultat;

import ProgrammeringsEksamenAPI.models.Deltager;
import ProgrammeringsEksamenAPI.models.Disciplin;
import ProgrammeringsEksamenAPI.models.Resultat;
import ProgrammeringsEksamenAPI.models.resultatModels.PointResultat;
import ProgrammeringsEksamenAPI.models.resultatModels.TimeDistanceResultat;
import ProgrammeringsEksamenAPI.models.resultatModels.TimeResultat;
import ProgrammeringsEksamenAPI.repository.DeltagerRepository;
import ProgrammeringsEksamenAPI.repository.DisciplinRepository;
import ProgrammeringsEksamenAPI.repository.ResultatRepository;
import dto.resultat.ResultatDTO;
import dto.resultat.ResultatRequest;
import dto.resultat.TimeDistanceResultatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ResultatServiceImpl implements ResultatServiceInterface {

    @Autowired
    private DisciplinRepository disciplinRepository;

    @Autowired
    private DeltagerRepository deltagerRepository;

    @Autowired
    private ResultatRepository resultatRepository;

    @Override
    public ResponseEntity<?> addPointsResultat(Long disciplinId, Long deltagerId, ResultatRequest request) {
        Integer points = request.getPoints();
        LocalDate dato = request.getDato();

        return addResultat(disciplinId, deltagerId, points, dato, null, "point", null);
    }

    @Override
    public ResponseEntity<?> addTimeResultat(Long disciplinId, Long deltagerId, ResultatRequest request) {
        LocalDate dato = request.getDato();
        Float timeTaken = request.getTimeTaken();

        return addResultat(disciplinId, deltagerId, null, dato, timeTaken, "time", null);
    }

    @Override
    public ResponseEntity<?> addDistanceResultat(Long disciplinId, Long deltagerId, ResultatRequest request) {
        LocalDate dato = request.getDato();
        Float timeTaken = request.getTimeTaken();
        Double distance = request.getDistance();

        return addResultat(disciplinId, deltagerId, null, dato, timeTaken, "time_distance", distance);
    }


    @Override
    public Resultat patchResultat(Long resultatId, Resultat updatedResultat) {
        Resultat existingResultat = resultatRepository.findById(resultatId).orElse(null);

        if (existingResultat == null) {
            return null;
        }

        if (updatedResultat.getTimeTaken() != null) {
            existingResultat.setTimeTaken(updatedResultat.getTimeTaken());
        }

        if (updatedResultat.getPoints() != null) {
            existingResultat.setPoints(updatedResultat.getPoints());
        }

        if (updatedResultat.getDistance() != null) {
            existingResultat.setDistance(updatedResultat.getDistance());
        }

        return resultatRepository.save(existingResultat);
    }

    @Override
    public List<Map<String, Object>> getResultaterForDeltager(Long deltagerId) {
        return resultatRepository.findResultaterWithDisciplinByDeltagerId(deltagerId);
    }

    @Override
    public List<ResponseEntity<?>> registrerResultater(Long disciplinId, List<ResultatRequest> requests) {
        List<ResponseEntity<?>> responses = new ArrayList<>();
        for (ResultatRequest request : requests) {
            Long deltagerId = request.getDeltagerId();
            ResponseEntity<?> response;
            switch (request.getResultatType()) {
                case "point":
                    response = addPointsResultat(disciplinId, deltagerId, request);
                    break;
                case "time":
                    response = addTimeResultat(disciplinId, deltagerId, request);
                    break;
                case "distance":
                    response = addDistanceResultat(disciplinId, deltagerId, request);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid resultat type");
            }
            responses.add(response);
        }
        return responses;
    }

    private ResponseEntity<?> addResultat(Long disciplinId, Long deltagerId, Integer points, LocalDate dato, Float timeTaken, String resultatType, Double distance) {
        Disciplin disciplin = fetchDisciplin(disciplinId);
        Deltager deltager = fetchDeltager(deltagerId);

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
                TimeDistanceResultat timeDistanceResultat = new TimeDistanceResultat();
                timeDistanceResultat.setTimeTaken(timeTaken);
                timeDistanceResultat.setDistance(distance);
                resultat = timeDistanceResultat;
                break;
            default:
                throw new IllegalArgumentException("Invalid resultat type");
        }

        resultat.setDato(dato);
        resultat.setDisciplin(disciplin);
        resultat.setDeltager(deltager);

        disciplin.addResult(resultat);
        disciplinRepository.save(disciplin);

        return ResponseEntity.status(HttpStatus.CREATED).body(resultat);
    }

    private Disciplin fetchDisciplin(Long disciplinId) {
        return disciplinRepository.findById(disciplinId)
                .orElseThrow(() -> new IllegalArgumentException("Disciplin not found"));
    }

    private Deltager fetchDeltager(Long deltagerId) {
        return deltagerRepository.findById(deltagerId)
                .orElseThrow(() -> new IllegalArgumentException("Deltager not found"));
    }
}
