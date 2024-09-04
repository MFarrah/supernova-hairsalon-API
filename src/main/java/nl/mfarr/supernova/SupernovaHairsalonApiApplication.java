package nl.mfarr.supernova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "nl.mfarr.supernova.repositories")
@EntityScan(basePackages = "nl.mfarr.supernova.models")
public class SupernovaHairsalonApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupernovaHairsalonApiApplication.class, args);
    }

}
