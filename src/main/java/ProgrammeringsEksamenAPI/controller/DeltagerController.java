package ProgrammeringsEksamenAPI.controller;


import ProgrammeringsEksamenAPI.models.Deltager;
import ProgrammeringsEksamenAPI.models.Disciplin;
import ProgrammeringsEksamenAPI.repository.DeltagerRepository;
import ProgrammeringsEksamenAPI.repository.DisciplinRepository;
import dto.deltager.DeltagerDTO;
import dto.deltager.DeltagerMedDisciplinerDTO;
import dto.deltager.DeltagerWithDisciplinDTO;
import dto.disciplin.DisciplinDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import util.ResourceNotFoundException;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/deltagere")
public class DeltagerController {

    private final DeltagerRepository deltagerRepository;
    private final DisciplinRepository disciplinRepository;

    public DeltagerController(DeltagerRepository deltagerRepository, DisciplinRepository disciplinRepository) {
        this.deltagerRepository = deltagerRepository;
        this.disciplinRepository = disciplinRepository;
    }


    @GetMapping
    public List<Deltager> getAllDeltagere() {
        return deltagerRepository.findAll();
    }

  /*  @PostMapping
    public Deltager createDeltager(@Valid @RequestBody Deltager deltager) {
        return deltagerRepository.save(deltager);
    }*/



 /*   @PostMapping
    public ResponseEntity<Deltager> createDeltager(@Valid @RequestBody DeltagerWithDisciplinDTO deltagerRequest) {
        Deltager deltager = new Deltager();
        deltager.setNavn(deltagerRequest.getNavn());
        deltager.setKøn(deltagerRequest.getKøn()); // Assuming this should be set correctly
        deltager.setAlder(deltagerRequest.getAlder());
        deltager.setKlub(deltagerRequest.getKlub());

        // Fetch Disciplin objects from repository based on ids
        List<Disciplin> discipliner = new ArrayList<>();
        for (Long disciplinId : deltagerRequest.getDisciplinerIds()) {
            Disciplin disciplin = disciplinRepository.findById(disciplinId)
                    .orElseThrow(() -> new ResourceNotFoundException("Disciplin not found with id: " + disciplinId));
            discipliner.add(disciplin);
        }

        deltager.setDiscipliner(discipliner);

        Deltager savedDeltager = deltagerRepository.save(deltager);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDeltager);
    }*/




    @PostMapping
    public ResponseEntity<DeltagerDTO> createDeltager(@Valid @RequestBody DeltagerWithDisciplinDTO deltagerRequest) {
        Deltager deltager = new Deltager();
        deltager.setNavn(deltagerRequest.getNavn());
        deltager.setKøn(deltagerRequest.getKøn());
        deltager.setAlder(deltagerRequest.getAlder());
        deltager.setKlub(deltagerRequest.getKlub());

        List<Disciplin> discipliner = new ArrayList<>();
        for (Long disciplinId : deltagerRequest.getDisciplinerIds()) {
            Disciplin disciplin = disciplinRepository.findById(disciplinId)
                    .orElseThrow(() -> new ResourceNotFoundException("Disciplin not found with id: " + disciplinId));
            discipliner.add(disciplin);
        }

        deltager.setDiscipliner(discipliner);

        Deltager savedDeltager = deltagerRepository.save(deltager);

        // Konverterer savedDeltager til DeltagerDTO
        DeltagerDTO deltagerDTO = new DeltagerDTO();

        deltagerDTO.setNavn(savedDeltager.getNavn());
        deltagerDTO.setKøn(savedDeltager.getKøn());
        deltagerDTO.setAlder(savedDeltager.getAlder());
        deltagerDTO.setKlub(savedDeltager.getKlub());

        List<Long> disciplinerIds = savedDeltager.getDiscipliner().stream()
                .map(Disciplin::getId)
                .collect(Collectors.toList());
        deltagerDTO.setDisciplinerIds(disciplinerIds);

        return ResponseEntity.status(HttpStatus.CREATED).body(deltagerDTO);
    }



    @PatchMapping("/{id}")
    public ResponseEntity<DeltagerDTO> updateDeltager(
            @PathVariable Long id,
            @Valid @RequestBody DeltagerWithDisciplinDTO deltagerRequest) {

        Deltager deltager = deltagerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deltager not found with id: " + id));

        deltager.setNavn(deltagerRequest.getNavn());
        deltager.setKøn(deltagerRequest.getKøn());
        deltager.setAlder(deltagerRequest.getAlder());
        deltager.setKlub(deltagerRequest.getKlub());

        List<Disciplin> discipliner = new ArrayList<>();
        for (Long disciplinId : deltagerRequest.getDisciplinerIds()) {
            Disciplin disciplin = disciplinRepository.findById(disciplinId)
                    .orElseThrow(() -> new ResourceNotFoundException("Disciplin not found with id: " + disciplinId));
            discipliner.add(disciplin);
        }

        deltager.setDiscipliner(discipliner);

        Deltager updatedDeltager = deltagerRepository.save(deltager);

        // Convert updatedDeltager to DeltagerDTO
        DeltagerDTO deltagerDTO = new DeltagerDTO();
        deltagerDTO.setId(updatedDeltager.getId());
        deltagerDTO.setNavn(updatedDeltager.getNavn());
        deltagerDTO.setKøn(updatedDeltager.getKøn());
        deltagerDTO.setAlder(updatedDeltager.getAlder());
        deltagerDTO.setKlub(updatedDeltager.getKlub());

        List<Long> disciplinerIds = updatedDeltager.getDiscipliner().stream()
                .map(Disciplin::getId)
                .collect(Collectors.toList());
        deltagerDTO.setDisciplinerIds(disciplinerIds);

        return ResponseEntity.ok().body(deltagerDTO);
    }




    @GetMapping("/{id}")
    public ResponseEntity<Deltager> getDeltagerById(@PathVariable(value = "id") Long deltagerId) {
        Deltager deltager = deltagerRepository.findById(deltagerId)
                .orElseThrow(() -> new ResourceNotFoundException("Deltager not found with id: " + deltagerId));

        return ResponseEntity.ok().body(deltager);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeltager(@PathVariable(value = "id") Long deltagerId) {
        Deltager deltager = deltagerRepository.findById(deltagerId)
                .orElseThrow(() -> new ResourceNotFoundException("Deltager not found with id: " + deltagerId));

        deltagerRepository.delete(deltager);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/search")
    public ResponseEntity<List<Deltager>> searchDeltagereByName(@RequestParam(value = "navn") String navn) {
        List<Deltager> deltagerList = deltagerRepository.findByNavnContainingIgnoreCase(navn);

        if (deltagerList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(deltagerList);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<DeltagerMedDisciplinerDTO>> filterDeltagere(
            @RequestParam(required = false) String køn,
            @RequestParam(required = false) Integer minAlder,
            @RequestParam(required = false) Integer maxAlder,
            @RequestParam(required = false) String klub,
            @RequestParam(required = false) String disciplin,
            @RequestParam(required = false) String navn,
            @RequestParam(required = false) String alderGroup) {

        // Convert empty strings to null
        køn = (køn != null && køn.isEmpty()) ? null : køn;
        klub = (klub != null && klub.isEmpty()) ? null : klub;
        disciplin = (disciplin != null && disciplin.isEmpty()) ? null : disciplin;
        navn = (navn != null && navn.isEmpty()) ? null : navn;

        List<Deltager> deltagerList = deltagerRepository.filterDeltagere(køn, minAlder, maxAlder, klub, disciplin, navn);


        if (alderGroup != null && !alderGroup.isEmpty()) {
            deltagerList = deltagerList.stream()
                    .filter(deltager -> deltager.getAlderGroup().equalsIgnoreCase(alderGroup))
                    .collect(Collectors.toList());
        }

        if (deltagerList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Convert entities to DTOs
        List<DeltagerMedDisciplinerDTO> dtoList = deltagerList.stream()
                .map(this::convertToDeltagerMedDisciplinerDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(dtoList);
    }


    // Helper method to convert Deltager entity to DeltagerDTO
    private DeltagerDTO convertToDto(Deltager deltager) {
        DeltagerDTO dto = new DeltagerDTO();
        dto.setId(deltager.getId());
        dto.setNavn(deltager.getNavn());
        dto.setKøn(deltager.getKøn());
        dto.setAlder(deltager.getAlder());
        dto.setKlub(deltager.getKlub());

        List<Long> disciplinerIds = deltager.getDiscipliner().stream()
                .map(Disciplin::getId)
                .collect(Collectors.toList());
        dto.setDisciplinerIds(disciplinerIds);

        return dto;
    }


    private DeltagerMedDisciplinerDTO convertToDeltagerMedDisciplinerDTO(Deltager deltager) {
        DeltagerMedDisciplinerDTO dto = new DeltagerMedDisciplinerDTO();
        dto.setId(deltager.getId());
        dto.setNavn(deltager.getNavn());
        dto.setKøn(deltager.getKøn());
        dto.setAlder(deltager.getAlder());
        dto.setKlub(deltager.getKlub());

        List<DisciplinDTO> disciplinDTOs = deltager.getDiscipliner().stream().map(disciplin -> {
            DisciplinDTO disciplinDTO = new DisciplinDTO();
            disciplinDTO.setId(disciplin.getId());
            disciplinDTO.setNavn(disciplin.getNavn());
            disciplinDTO.setResultattype(disciplin.getResultattype());
            return disciplinDTO;
        }).collect(Collectors.toList());

        dto.setDisciplinerIds(disciplinDTOs);
        return dto;
    }





  /*  @GetMapping("/{id}")
    public ResponseEntity<Deltager> getDeltagerById(@PathVariable(value = "id") Long deltagerId) {
        Deltager deltager = deltagerRepository.findById(deltagerId)
                .orElseThrow(() -> new ResourceNotFoundException("Deltager not found for this id :: " + deltagerId));
        return ResponseEntity.ok().body(deltager);
    }*/

    // Other CRUD operations can be added here
}
