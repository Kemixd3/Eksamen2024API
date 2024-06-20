package ProgrammeringsEksamenAPI.controller;

import ProgrammeringsEksamenAPI.models.Disciplin;
import ProgrammeringsEksamenAPI.services.disciplin.DisciplinService;
import dto.disciplin.DisciplinDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/discipliner")
public class DisciplinController {

    @Autowired
    private DisciplinService disciplinService;

    @PostMapping
    public ResponseEntity<Disciplin> createDisciplin(@Valid @RequestBody DisciplinDTO dto) {
        Disciplin savedDisciplin = disciplinService.createDisciplin(dto);
        return ResponseEntity.ok(savedDisciplin);
    }
}

