package ProgrammeringsEksamenAPI.services.resultat;


import ProgrammeringsEksamenAPI.models.Deltager;
import ProgrammeringsEksamenAPI.models.Disciplin;
import ProgrammeringsEksamenAPI.models.Resultat;
import ProgrammeringsEksamenAPI.models.ResultatTypes;
import ProgrammeringsEksamenAPI.models.resultatModels.DistanceResultat;
import ProgrammeringsEksamenAPI.models.resultatModels.PointResultat;
import ProgrammeringsEksamenAPI.models.resultatModels.TimeDistanceResultat;
import ProgrammeringsEksamenAPI.models.resultatModels.TimeResultat;
import ProgrammeringsEksamenAPI.repository.DeltagerRepository;
import ProgrammeringsEksamenAPI.repository.DisciplinRepository;
import ProgrammeringsEksamenAPI.repository.ResultatRepository;
import dto.resultat.ResultatDTO;
import dto.resultat.TimeDistanceResultatDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.GlobalExceptionHandler;

@Service
public class ResultatService {

    @Autowired
    private ResultatRepository resultatRepository;

    @Autowired
    private DeltagerRepository deltagerRepository;

    @Autowired
    private DisciplinRepository disciplinRepository;

    @Transactional
    public TimeDistanceResultat createTimeDistanceResultat(TimeDistanceResultatDTO dto) {
        Deltager deltager = deltagerRepository.findById(dto.getDeltagerId())
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Deltager not found for this id :: " + dto.getDeltagerId()));
        Disciplin disciplin = disciplinRepository.findById(dto.getDisciplinId())
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Disciplin not found for this id :: " + dto.getDisciplinId()));

        TimeDistanceResultat resultat = new TimeDistanceResultat();
        resultat.setDeltager(deltager);
        resultat.setDisciplin(disciplin);
        resultat.setDato(dto.getDato());
        resultat.setTimeTaken(dto.getTime());
        resultat.setDistance(dto.getDistance());

        return resultatRepository.save(resultat);
    }


    @Transactional
    public void deleteResultat(Long deltagerId, Long resultatId) {
        resultatRepository.deleteByIdAndDeltagerId(resultatId, deltagerId);
    }


    @Transactional
    public ResultatDTO createResultat(ResultatDTO resultatDTO) {
        Deltager deltager = deltagerRepository.findById(resultatDTO.getDeltagerId())
                .orElseThrow(() -> new EntityNotFoundException("Deltager with id " + resultatDTO.getDeltagerId() + " not found"));

        Disciplin disciplin = disciplinRepository.findById(resultatDTO.getDisciplinId())
                .orElseThrow(() -> new EntityNotFoundException("Disciplin with id " + resultatDTO.getDisciplinId() + " not found"));

        Resultat resultat;
        switch (resultatDTO.getResultatType()) {
         /*   case TIME:
                TimeResultat timeResultat = new TimeResultat();
                timeResultat.setDato(resultatDTO.getTid());
                resultat = timeResultat;
                break;*/
            case DISTANCE:
                DistanceResultat distanceResultat = new DistanceResultat();
                distanceResultat.setDistance(resultatDTO.getAfstand());
                resultat = distanceResultat;
                break;
            case POINT:
                PointResultat pointResultat = new PointResultat();
                pointResultat.setPoints(resultatDTO.getPoint());
                resultat = pointResultat;
                break;
            default:
                throw new IllegalArgumentException("Invalid resultat type: " + resultatDTO.getResultatType());
        }

        resultat.setDato(resultatDTO.getDato());
        resultat.setDeltager(deltager);
        resultat.setDisciplin(disciplin);

        resultatRepository.save(resultat);

        return resultatDTO;
    }












    // Similar methods for other result types
}
