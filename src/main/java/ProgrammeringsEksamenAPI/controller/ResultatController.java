package ProgrammeringsEksamenAPI.controller;

import ProgrammeringsEksamenAPI.models.Deltager;
import ProgrammeringsEksamenAPI.models.Disciplin;
import ProgrammeringsEksamenAPI.models.Resultat;
import ProgrammeringsEksamenAPI.models.resultatModels.PointResultat;
import ProgrammeringsEksamenAPI.models.resultatModels.TimeDistanceResultat;
import ProgrammeringsEksamenAPI.models.resultatModels.TimeResultat;
import ProgrammeringsEksamenAPI.repository.DeltagerRepository;
import ProgrammeringsEksamenAPI.repository.DisciplinRepository;
import ProgrammeringsEksamenAPI.services.resultat.ResultatServiceInterface;
import dto.resultat.PointResultatDTO;
import dto.resultat.ResultatDTO;
import dto.resultat.ResultatRequest;
import dto.resultat.TimeDistanceResultatDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ProgrammeringsEksamenAPI.services.resultat.ResultatService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/resultater")
public class ResultatController {

    @Autowired
    private ResultatService resultatService;

    @Autowired
    private ResultatServiceInterface resultatServiceInterface;

    @Autowired
    private DisciplinRepository disciplinRepository;

    @Autowired
    private DeltagerRepository deltagerRepository;

    @PostMapping("/time-distance")
    public ResponseEntity<TimeDistanceResultat> createTimeDistanceResultat(@Valid @RequestBody TimeDistanceResultatDTO dto) {
        TimeDistanceResultat savedResultat = resultatService.createTimeDistanceResultat(dto);
        return ResponseEntity.ok(savedResultat);
    }

    @PostMapping
    public ResponseEntity<ResultatDTO> createResultat(@Valid @RequestBody ResultatDTO resultatDTO) {
        ResultatDTO createdResultat = resultatService.createResultat(resultatDTO);
        return ResponseEntity.ok(createdResultat);
    }




    @PostMapping("/time/{disciplinId}/{deltagerId}")
    public ResponseEntity<?> addTimeResultat(@PathVariable Long disciplinId, @PathVariable Long deltagerId, @RequestBody TimeResultat request) {
        Optional<Disciplin> disciplinOptional = disciplinRepository.findById(disciplinId);
        Optional<Deltager> deltagerOptional = deltagerRepository.findById(deltagerId);

        if (disciplinOptional.isEmpty() || deltagerOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Disciplin disciplin = disciplinOptional.get();
        Deltager deltager = deltagerOptional.get();

        TimeResultat timeResultat = new TimeResultat();
        timeResultat.setDato(request.getDato());
        timeResultat.setTimeTaken(request.getTimeTaken());
        timeResultat.setDisciplin(disciplin);
        timeResultat.setDeltager(deltager);

        // Save the result
        disciplin.addResult(timeResultat);
        disciplinRepository.save(disciplin);

        return ResponseEntity.status(HttpStatus.CREATED).body(timeResultat);
    }


    @PostMapping("/point/{disciplinId}/{deltagerId}")
    public ResponseEntity<?> addPointsResultat(@PathVariable Long disciplinId, @PathVariable Long deltagerId, @RequestBody PointResultat request) {
        Optional<Disciplin> disciplinOptional = disciplinRepository.findById(disciplinId);
        Optional<Deltager> deltagerOptional = deltagerRepository.findById(deltagerId);

        if (disciplinOptional.isEmpty() || deltagerOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Disciplin disciplin = disciplinOptional.get();
        Deltager deltager = deltagerOptional.get();

        PointResultat pointsResultat = new PointResultat();
        pointsResultat.setDato(request.getDato());
        pointsResultat.setPoints(request.getPoints());
        pointsResultat.setDisciplin(disciplin);
        pointsResultat.setDeltager(deltager);

        // Save the result
        disciplin.addResult(pointsResultat);
        disciplinRepository.save(disciplin);

        // Convert entity to DTO to return
        PointResultatDTO pointsResultatDTO = new PointResultatDTO();
        pointsResultatDTO.setId(pointsResultat.getId());
        pointsResultatDTO.setDato(pointsResultat.getDato());
        pointsResultatDTO.setPoints(pointsResultat.getPoints());
        pointsResultatDTO.setDisciplinId(disciplin.getId());
        pointsResultatDTO.setDeltagerId(deltager.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(pointsResultatDTO);
    }



    @GetMapping("/{deltagerId}/resultater")
    public ResponseEntity<List<Resultat>> getResultaterForDeltager(@PathVariable Long deltagerId) {
        Optional<Deltager> deltagerOptional = deltagerRepository.findById(deltagerId);

        if (deltagerOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Deltager deltager = deltagerOptional.get();
        List<Resultat> resultater = new ArrayList<>();

        // Add logic to fetch resultater for the deltager
        for (Disciplin disciplin : deltager.getDiscipliner()) {
            resultater.addAll(disciplin.getResultater().stream()
                    .filter(resultat -> resultat.getDeltager().equals(deltager))
                    .collect(Collectors.toList()));
        }

        return ResponseEntity.ok(resultater);
    }




    @PostMapping("/distance/{disciplinId}/{deltagerId}")
    public ResponseEntity<?> addDistanceResultat(@PathVariable Long disciplinId, @PathVariable Long deltagerId, @RequestBody TimeDistanceResultat request) {
        Optional<Disciplin> disciplinOptional = disciplinRepository.findById(disciplinId);
        Optional<Deltager> deltagerOptional = deltagerRepository.findById(deltagerId);

        if (disciplinOptional.isEmpty() || deltagerOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Disciplin disciplin = disciplinOptional.get();
        Deltager deltager = deltagerOptional.get();

        TimeDistanceResultat distanceResultat = new TimeDistanceResultat();
        distanceResultat.setDato(request.getDato());
        distanceResultat.setTimeTaken(request.getTimeTaken());
        distanceResultat.setDistance(request.getDistance());
        distanceResultat.setDisciplin(disciplin);
        distanceResultat.setDeltager(deltager);

        // Save the result
        disciplin.addResult(distanceResultat);
        disciplinRepository.save(disciplin);

        return ResponseEntity.status(HttpStatus.CREATED).body(distanceResultat);
    }









    @PostMapping("/registrer/{disciplinId}")
    public ResponseEntity<?> registrerResultater(
            @PathVariable Long disciplinId,
            @RequestBody List<ResultatRequest> requests
    ) {
        List<ResponseEntity<?>> responses = new ArrayList<>();
        for (ResultatRequest request : requests) {
            Long deltagerId = request.getDeltagerId(); // Get deltagerId from each ResultatRequest
            ResponseEntity<?> response;
            switch (request.getResultatType()) {
                case "point":
                    response = resultatServiceInterface.addPointsResultat(disciplinId, deltagerId, request);
                    break;
                case "time":
                    response = resultatServiceInterface.addTimeResultat(disciplinId, deltagerId, request);
                    break;
                case "time_distance":
                    response = resultatServiceInterface.addDistanceResultat(disciplinId, deltagerId, request);
                    break;
                default:
                    return ResponseEntity.badRequest().body("Invalid resultat type");
            }
            responses.add(response);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }



}
