package util.dataSeeder;

import ProgrammeringsEksamenAPI.models.Disciplin;
import ProgrammeringsEksamenAPI.models.resultatModels.ResultatTypeEnum;
import ProgrammeringsEksamenAPI.repository.DisciplinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class DisciplinDataSeeder {

    @Autowired
    private DisciplinRepository disciplinRepository;

    private final Map<String, ResultatTypeEnum> disciplinMap = Map.ofEntries(
            Map.entry("1-milløb (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("10.000-meterløb (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("100-meterløb (atletik)", ResultatTypeEnum.TIME),
            Map.entry("110 meter hækkeløb", ResultatTypeEnum.TIME),
            Map.entry("1500-meterløb (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("200 meter hækkeløb", ResultatTypeEnum.TIME),
            Map.entry("200-meterløb (atletik)", ResultatTypeEnum.TIME),
            Map.entry("3000-meterløb (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("4 × 100-meterløb (atletik)", ResultatTypeEnum.TIME),
            Map.entry("4 × 400-meterløb (atletik)", ResultatTypeEnum.TIME),
            Map.entry("400 meter hækkeløb", ResultatTypeEnum.TIME),
            Map.entry("400-meterløb (atletik)", ResultatTypeEnum.TIME),
            Map.entry("4x400-meterløb blandet hold (atletik)", ResultatTypeEnum.TIME),
            Map.entry("5000-meter-løb (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("60 meter hækkeløb", ResultatTypeEnum.TIME),
            Map.entry("60-meterløb (atletik)", ResultatTypeEnum.TIME),
            Map.entry("800-meterløb (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("Cross (løbesport)", ResultatTypeEnum.DISTANCE),
            Map.entry("Diskoskast (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("Femkamp (atletik)", ResultatTypeEnum.POINT),
            Map.entry("Forhindringsløb (atletik)", ResultatTypeEnum.TIME),
            Map.entry("Halvmaratonløb (løbesport)", ResultatTypeEnum.DISTANCE),
            Map.entry("Hammerkast (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("Højdespring (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("Højdespring uden tilløb (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("Kastefemkamp (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("Kastetrekamp", ResultatTypeEnum.DISTANCE),
            Map.entry("Kuglestød (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("Kørestolsrace", ResultatTypeEnum.DISTANCE),
            Map.entry("Længdespring (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("Længdespring uden tilløb (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("Maratonløb (løbesport)", ResultatTypeEnum.DISTANCE),
            Map.entry("Slyngboldkast (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("Spydkast (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("Stangspring (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("Syvkamp (atletik)", ResultatTypeEnum.POINT),
            Map.entry("Tikamp (atletik)", ResultatTypeEnum.POINT),
            Map.entry("Tovtrækning", ResultatTypeEnum.DISTANCE),
            Map.entry("Trail (løbesport)", ResultatTypeEnum.DISTANCE),
            Map.entry("Trespring (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("Trespring uden tilløb (atletik)", ResultatTypeEnum.DISTANCE),
            Map.entry("Vægtkast (atletik)", ResultatTypeEnum.DISTANCE)
    );

    @Transactional
    public void seedDiscipliner() {
        disciplinMap.forEach((navn, resultattype) -> {
            Disciplin disciplin = disciplinRepository.findByNavn(navn);
            if (disciplin == null) {
                disciplin = new Disciplin();
                disciplin.setNavn(navn);
                // Gem enum name som String i databasen
                disciplin.setResultattype(resultattype.name());
                disciplinRepository.save(disciplin);
            }
        });
    }
}