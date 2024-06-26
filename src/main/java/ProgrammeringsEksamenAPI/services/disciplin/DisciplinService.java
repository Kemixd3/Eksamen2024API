package ProgrammeringsEksamenAPI.services.disciplin;

import ProgrammeringsEksamenAPI.models.Deltager;
import ProgrammeringsEksamenAPI.models.Disciplin;
import ProgrammeringsEksamenAPI.models.Resultat;
import ProgrammeringsEksamenAPI.repository.DeltagerRepository;
import ProgrammeringsEksamenAPI.repository.DisciplinRepository;
import dto.disciplin.DisciplinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisciplinService {

    @Autowired
    private DisciplinRepository disciplinRepository;


    @Autowired
    private DeltagerRepository deltagerRepository;

/*    public List<Disciplin> findAll() {
        return disciplinRepository.findAll();
    }*/



    public void registerResult(Long deltagerId, Long disciplinId, Resultat resultat) {
        Deltager deltager = deltagerRepository.findById(deltagerId)
                .orElseThrow(() -> new IllegalArgumentException("Deltager not found with id: " + deltagerId));

        Disciplin disciplin = disciplinRepository.findById(disciplinId)
                .orElseThrow(() -> new IllegalArgumentException("Disciplin not found with id: " + disciplinId));

        // Add the result to the discipline
        disciplin.addResult(resultat);

        // Assign the result to the participant
        resultat.setDeltager(deltager);

        // Save the updated discipline (which cascades to result saving)
        disciplinRepository.save(disciplin);
    }

    public List<DisciplinDTO> findAll() {
        List<Disciplin> discipliner = disciplinRepository.findAll();
        return discipliner.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private DisciplinDTO mapToDTO(Disciplin disciplin) {
        DisciplinDTO disciplinDTO = new DisciplinDTO();
        disciplinDTO.setId(disciplin.getId());
        disciplinDTO.setNavn(disciplin.getNavn());
        disciplinDTO.setResultattype(disciplin.getResultattype());
        // Map other fields as needed
        return disciplinDTO;
    }

    @Transactional
    public Disciplin createDisciplin(DisciplinDTO dto) {
        Disciplin disciplin = new Disciplin();
        disciplin.setNavn(dto.getNavn());
        disciplin.setResultattype(dto.getResultattype());
        return disciplinRepository.save(disciplin);
    }
}
