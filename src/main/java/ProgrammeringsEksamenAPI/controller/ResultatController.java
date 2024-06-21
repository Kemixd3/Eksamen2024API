package ProgrammeringsEksamenAPI.controller;

import ProgrammeringsEksamenAPI.models.Deltager;
import ProgrammeringsEksamenAPI.models.Disciplin;
import ProgrammeringsEksamenAPI.models.Resultat;
import ProgrammeringsEksamenAPI.models.resultatModels.PointResultat;
import ProgrammeringsEksamenAPI.models.resultatModels.TimeDistanceResultat;
import ProgrammeringsEksamenAPI.models.resultatModels.TimeResultat;
import ProgrammeringsEksamenAPI.repository.DeltagerRepository;
import ProgrammeringsEksamenAPI.repository.DisciplinRepository;
import ProgrammeringsEksamenAPI.repository.ResultatRepository;
import ProgrammeringsEksamenAPI.services.resultat.ResultatServiceInterface;
import dto.resultat.PointResultatDTO;
import dto.resultat.ResultatDTO;
import dto.resultat.ResultatRequest;
import dto.resultat.TimeDistanceResultatDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ProgrammeringsEksamenAPI.services.resultat.ResultatService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/resultater")
public class ResultatController {

    @Autowired
    private ResultatService resultatService;

    @Autowired
    private ResultatServiceInterface resultatServiceInterface;

    private static final Logger logger = LoggerFactory.getLogger(DeltagerController.class);

    @Autowired
    private DisciplinRepository disciplinRepository;

    @Autowired
    private DeltagerRepository deltagerRepository;

    @Autowired
    private ResultatRepository resultatRepository;

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


    @PatchMapping("/{resultatId}")
    public ResponseEntity<Resultat> patchResultat(
            @PathVariable Long resultatId,
            @RequestBody Resultat updatedResultat
    ) {

        Resultat existingResultat = resultatRepository.findById(resultatId)
                .orElse(null);

        if (existingResultat == null) {
            return ResponseEntity.notFound().build();
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


        Resultat savedResultat = resultatRepository.save(existingResultat);
        return ResponseEntity.ok(savedResultat);
    }


    @GetMapping("/{deltagerId}/resultater")
    public ResponseEntity<List<Map<String, Object>>> getResultaterForDeltager(@PathVariable Long deltagerId) {
        Optional<Deltager> deltagerOptional = deltagerRepository.findById(deltagerId);

        if (deltagerOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Fetch results along with discipline information
        List<Map<String, Object>> resultater = resultatRepository.findResultaterWithDisciplinByDeltagerId(deltagerId);

        return ResponseEntity.ok(resultater);
    }


    @DeleteMapping("/{deltagerId}/resultater/{resultatId}")
    public ResponseEntity<?> deleteResultat(
            @PathVariable Long deltagerId,
            @PathVariable Long resultatId
    ) {
        try {
            resultatService.deleteResultat(deltagerId, resultatId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
                case "distance":
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
