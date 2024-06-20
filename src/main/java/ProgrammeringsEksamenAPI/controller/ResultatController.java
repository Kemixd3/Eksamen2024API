package ProgrammeringsEksamenAPI.controller;

import ProgrammeringsEksamenAPI.models.resultatModels.TimeDistanceResultat;
import dto.resultat.ResultatDTO;
import dto.resultat.TimeDistanceResultatDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ProgrammeringsEksamenAPI.services.resultat.ResultatService;

@RestController
@RequestMapping("/api/resultater")
public class ResultatController {

    @Autowired
    private ResultatService resultatService;

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


}
