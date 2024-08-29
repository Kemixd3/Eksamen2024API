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


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/resultater")
public class ResultatController {

    @Autowired
    private ResultatServiceInterface resultatService;



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

    @PatchMapping("/{resultatId}")
    public ResponseEntity<Resultat> patchResultat(
            @PathVariable Long resultatId,
            @RequestBody Resultat updatedResultat
    ) {
        Resultat savedResultat = resultatService.patchResultat(resultatId, updatedResultat);
        if (savedResultat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(savedResultat);
    }

    @GetMapping("/{deltagerId}/resultater")
    public ResponseEntity<List<Map<String, Object>>> getResultaterForDeltager(@PathVariable Long deltagerId) {
        List<Map<String, Object>> resultater = resultatService.getResultaterForDeltager(deltagerId);
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

    @PostMapping("/registrer/{disciplinId}")
    public ResponseEntity<?> registrerResultater(
            @PathVariable Long disciplinId,
            @RequestBody List<ResultatRequest> requests
    ) {
        List<ResponseEntity<?>> responses = resultatService.registrerResultater(disciplinId, requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @PostMapping("/point/{disciplinId}/{deltagerId}")
    public ResponseEntity<?> addPointsResultat(
            @PathVariable Long disciplinId,
            @PathVariable Long deltagerId,
            @Valid @RequestBody ResultatRequest request
    ) {
        return resultatService.addPointsResultat(disciplinId, deltagerId, request);
    }

    @PostMapping("/time/{disciplinId}/{deltagerId}")
    public ResponseEntity<?> addTimeResultat(
            @PathVariable Long disciplinId,
            @PathVariable Long deltagerId,
            @Valid @RequestBody ResultatRequest request
    ) {
        return resultatService.addTimeResultat(disciplinId, deltagerId, request);
    }

    @PostMapping("/distance/{disciplinId}/{deltagerId}")
    public ResponseEntity<?> addDistanceResultat(
            @PathVariable Long disciplinId,
            @PathVariable Long deltagerId,
            @Valid @RequestBody ResultatRequest request
    ) {
        return resultatService.addDistanceResultat(disciplinId, deltagerId, request);
    }
}
