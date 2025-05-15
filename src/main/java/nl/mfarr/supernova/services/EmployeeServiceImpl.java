package nl.mfarr.supernova.services;

import lombok.RequiredArgsConstructor;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl extends EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Override
    public String uploadProfileImage(Long employeeId, MultipartFile file) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Medewerker niet gevonden"));

        // Optioneel: alleen afbeeldingen toestaan
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Alleen afbeeldingsbestanden zijn toegestaan.");
        }

        // Bestandsgrootte limiet (bijvoorbeeld 5MB)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("Bestand is te groot. Maximaal 5MB toegestaan.");
        }

        // Bestandsnaam genereren
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filepath = Paths.get(uploadDir, filename);

        // Folder aanmaken indien nodig
        try {
            Files.createDirectories(filepath.getParent());
            Files.write(filepath, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Fout bij het opslaan van het bestand", e);
        }

        // URL instellen en opslaan
        String imageUrl = "/images/employees/" + filename;
        employee.setProfileImageUrl(imageUrl);
        employeeRepository.save(employee);

        return imageUrl;
    }
}