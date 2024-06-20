package ProgrammeringsEksamenAPI.services.disciplin;

import ProgrammeringsEksamenAPI.models.Disciplin;
import ProgrammeringsEksamenAPI.repository.DisciplinRepository;
import dto.disciplin.DisciplinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DisciplinService {

    @Autowired
    private DisciplinRepository disciplinRepository;

    @Transactional
    public Disciplin createDisciplin(DisciplinDTO dto) {
        Disciplin disciplin = new Disciplin();
        disciplin.setNavn(dto.getNavn());
        disciplin.setResultattype(dto.getResultattype());
        return disciplinRepository.save(disciplin);
    }
}
