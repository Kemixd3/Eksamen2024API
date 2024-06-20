package ProgrammeringsEksamenAPI.services.deltager;

import ProgrammeringsEksamenAPI.models.Deltager;
import ProgrammeringsEksamenAPI.repository.DeltagerRepository;
import dto.deltager.DeltagerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeltagerService {

    @Autowired
    private DeltagerRepository deltagerRepository;

    @Transactional
    public Deltager createDeltager(DeltagerDTO dto) {
        Deltager deltager = new Deltager();
        deltager.setNavn(dto.getNavn());
        deltager.setKøn(dto.getKøn());
        deltager.setAlder(dto.getAlder());
        deltager.setKlub(dto.getKlub());
        return deltagerRepository.save(deltager);
    }


}
