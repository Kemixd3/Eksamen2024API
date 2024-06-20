package ProgrammeringsEksamenAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import util.dataSeeder.DisciplinDataSeeder;

//@SpringBootApplication( exclude = {SecurityAutoConfiguration.class} )
@SpringBootApplication
@EntityScan(basePackages = {"ProgrammeringsEksamenAPI.security.entity", "ProgrammeringsEksamenAPI.models"})
@ComponentScan(basePackages = {"ProgrammeringsEksamenAPI", "util.dataSeeder"})

public class EksamenApiApplication implements CommandLineRunner {
    @Autowired
    private DisciplinDataSeeder disciplinDataSeeder;
    public static void main(String[] args) {
        SpringApplication.run(EksamenApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        disciplinDataSeeder.seedDiscipliner();
    }

}



