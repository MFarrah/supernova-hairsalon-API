package nl.mfarr.supernova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("nl.mfarr.supernova.repositories") // Specify your repository package if not in sub-package
public class SupernovaHairsalonApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SupernovaHairsalonApiApplication.class, args);
    }
}
