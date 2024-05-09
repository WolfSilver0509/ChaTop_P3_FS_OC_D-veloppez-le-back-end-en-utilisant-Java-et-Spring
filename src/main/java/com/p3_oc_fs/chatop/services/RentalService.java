package com.p3_oc_fs.chatop.services;

import com.p3_oc_fs.chatop.models.Rental;
import com.p3_oc_fs.chatop.repositorys.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    @Value("${app.base-url}")
    private String baseUrl;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    /* Méthode pour enregistrer une location avec une image */
    public void saveRentalWithImage(Rental rental, MultipartFile image) {
        String imageUrl = saveImage(image, resourceLoader);
        rental.setPicture(imageUrl);
        rentalRepository.save(rental);
    }

    /* Méthode pour sauvegarder une image */
    private String saveImage(MultipartFile image, ResourceLoader resourceLoader) {
        try {
            if (image.isEmpty()) {
                throw new RuntimeException("Le fichier image est vide");
            }

            String imageName = image.getOriginalFilename();
            String contentType = image.getContentType();
            if (!contentType.startsWith("image/")) {
                throw new RuntimeException("Le fichier n'est pas une image");
            }

            Path path = resourceLoader.getResource("classpath:static/images/").getFile().toPath().resolve(imageName);
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, image.getBytes());

            return baseUrl + "/images/" + imageName;
        } catch (IOException e) {
            throw new RuntimeException("Échec de l'enregistrement de l'image", e);
        }
    }

    /* Méthode pour récupérer toutes les locations */
    public List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    /* Méthode pour récupérer une location par son identifiant */
    public Optional<Rental> findById(Integer id) {
        return rentalRepository.findById(id);
    }

    /* Méthode pour sauvegarder une location */
    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental); // Enregistre l'objet de location mis à jour
    }
}
