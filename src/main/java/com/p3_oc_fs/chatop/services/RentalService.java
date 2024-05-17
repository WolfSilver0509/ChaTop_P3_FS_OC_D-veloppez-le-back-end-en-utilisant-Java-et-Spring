/*
 * Classe de service responsable de la gestion des locations.
 * Elle inclut des méthodes pour créer, mettre à jour, récupérer et enregistrer des locations.
 */
package com.p3_oc_fs.chatop.services;

import com.p3_oc_fs.chatop.dtos.RentalDtoUpdate; // Importation du DTO pour la mise à jour de location
import com.p3_oc_fs.chatop.models.Rental; // Importation du modèle Rental
import com.p3_oc_fs.chatop.repositorys.RentalRepository; // Importation du dépôt de locations
import org.springframework.beans.factory.annotation.Autowired; // Importation de l'annotation Autowired pour l'injection de dépendances
import org.springframework.beans.factory.annotation.Value; // Importation de l'annotation Value pour lire les valeurs des propriétés
import org.springframework.core.io.ResourceLoader; // Importation du chargeur de ressources pour gérer les fichiers
import org.springframework.stereotype.Service; // Importation de l'annotation Service pour marquer cette classe comme un service
import org.springframework.web.multipart.MultipartFile; // Importation de la classe MultipartFile pour la gestion des fichiers

import com.p3_oc_fs.chatop.dtos.CreateRentalResponseDto; // Importation du DTO pour la réponse de création de location
import com.p3_oc_fs.chatop.dtos.GetAllRentalsResponseDto; // Importation du DTO pour la réponse de récupération de toutes les locations
import com.p3_oc_fs.chatop.dtos.RentalDtoCreate; // Importation du DTO pour la création de location
import com.p3_oc_fs.chatop.dtos.RentalDtoGet; // Importation du DTO pour la récupération de location par ID
import com.p3_oc_fs.chatop.models.User; // Importation du modèle User
import org.springframework.http.HttpStatus; // Importation de l'énumération HttpStatus pour les statuts HTTP
import org.springframework.http.ResponseEntity; // Importation de la classe ResponseEntity pour les réponses HTTP
import org.springframework.security.core.Authentication; // Importation de la classe Authentication pour gérer les informations de l'utilisateur

import java.io.IOException; // Importation de la classe IOException pour gérer les exceptions d'entrée/sortie
import java.nio.file.Files; // Importation de la classe Files pour les opérations sur les fichiers
import java.nio.file.Path; // Importation de la classe Path pour les chemins de fichiers
import java.security.Principal; // Importation de la classe Principal pour gérer les informations d'utilisateur
import java.util.List; // Importation de la classe List pour les listes
import java.util.Optional; // Importation de la classe Optional pour gérer les valeurs potentielles nulles
import java.util.stream.Collectors; // Importation de la classe Collectors pour la collecte de flux

@Service // Indique que cette classe est un composant de service dans Spring
public class RentalService {

    @Value("${app.base-url}") // Injection de la valeur de l'URL de base à partir des propriétés de l'application
    private String baseUrl;

    @Autowired // Injection de dépendance pour RentalRepository
    private RentalRepository rentalRepository;

    @Autowired // Injection de dépendance pour ResourceLoader
    private ResourceLoader resourceLoader;

    /*
     * Méthode pour convertir une entité Rental en DTO RentalDtoGet.
     * Prend en entrée une entité Rental et retourne un DTO RentalDtoGet.
     */
    private RentalDtoGet convertToRentalDto(Rental rental) {
        return new RentalDtoGet(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getDescription(),
                rental.getOwner_id().getId(),
                rental.getCreated_at(),
                rental.getUpdated_at(),
                rental.getPicture()
        );
    }

    /*
     * Méthode pour créer une nouvelle location.
     * Prend en entrée un fichier image, un DTO de création de location, et les informations de l'utilisateur.
     * Retourne une réponse contenant le DTO de la réponse de création de location.
     */
    public ResponseEntity<CreateRentalResponseDto> createRental(MultipartFile picture,
                                                                RentalDtoCreate rentalDto,
                                                                Principal principal) {
        User currentUser = (User) ((Authentication) principal).getPrincipal(); // Récupération de l'utilisateur actuel
        Rental rental = new Rental(
                rentalDto.getName(),
                rentalDto.getSurface(),
                rentalDto.getPrice(),
                rentalDto.getDescription(),
                currentUser // Définition de l'utilisateur actuel comme propriétaire de la location
        );

        saveRentalWithImage(rental, picture); // Enregistrement de la location avec l'image
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateRentalResponseDto("Rental created!")); // Retour de la réponse de création
    }

    /*
     * Méthode pour récupérer toutes les locations.
     * Retourne une liste de toutes les entités Rental.
     */
    public List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    /*
     * Méthode pour récupérer une location par son ID.
     * Prend en entrée l'ID de la location.
     * Retourne un Optional contenant l'entité Rental si elle est trouvée.
     */
    public Optional<Rental> findById(Integer id) {
        return rentalRepository.findById(id);
    }

    /*
     * Méthode pour enregistrer une location.
     * Prend en entrée une entité Rental et la sauvegarde dans la base de données.
     * Retourne l'entité Rental enregistrée.
     */
    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    /*
     * Méthode pour enregistrer une location avec une image.
     * Prend en entrée une entité Rental et un fichier image.
     * Sauvegarde l'image et met à jour le chemin de l'image dans l'entité Rental.
     */
    public void saveRentalWithImage(Rental rental, MultipartFile image) {
        String imageUrl = saveImage(image, resourceLoader); // Sauvegarde de l'image et récupération de l'URL
        rental.setPicture(imageUrl); // Mise à jour de l'URL de l'image dans l'entité Rental
        rentalRepository.save(rental); // Enregistrement de l'entité Rental mise à jour
    }

    /*
     * Méthode pour récupérer toutes les locations et les convertir en DTO.
     * Retourne une réponse contenant une liste de DTO RentalDtoGet.
     */
    public ResponseEntity<GetAllRentalsResponseDto> getAllRentals() {
        List<Rental> rentals = findAllRentals(); // Récupération de toutes les locations
        List<RentalDtoGet> rentalDtos = rentals.stream()
                .map(this::convertToRentalDto) // Conversion des entités Rental en DTO
                .collect(Collectors.toList());
        return ResponseEntity.ok(new GetAllRentalsResponseDto(rentalDtos)); // Retour de la réponse avec la liste de DTO
    }

    /*
     * Méthode pour récupérer une location par son ID.
     * Prend en entrée l'ID de la location.
     * Retourne une réponse contenant le DTO de la location récupérée ou une réponse 404 si non trouvée.
     */
    public ResponseEntity<RentalDtoGet> getRentalById(Integer id) {
        Optional<Rental> rentalOptional = findById(id); // Récupération de la location par ID
        if (rentalOptional.isPresent()) {
            Rental rental = rentalOptional.get();
            RentalDtoGet rentalDto = convertToRentalDto(rental); // Conversion de l'entité Rental en DTO
            return ResponseEntity.ok(rentalDto); // Retour de la réponse avec le DTO de la location
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retour d'une réponse 404 si non trouvée
        }
    }

    /*
     * Méthode pour mettre à jour une location existante.
     * Prend en entrée l'ID de la location, un DTO de la location à mettre à jour, et les informations de l'utilisateur.
     * Retourne une réponse contenant le DTO de la location mise à jour ou une réponse d'erreur si non trouvée ou non autorisée.
     */
    public ResponseEntity<RentalDtoUpdate> updateRental(Integer id,
                                                        RentalDtoGet rentalDtoGet,
                                                        Principal principal) {
        Optional<Rental> existingRentalOptional = findById(id); // Récupération de la location par ID
        if (existingRentalOptional.isPresent()) {
            Rental existingRental = existingRentalOptional.get();
            if (!existingRental.getOwner_id().getUsername().equals(principal.getName())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new RentalDtoUpdate("You don't have permission to update this rental.")); // Retour d'une réponse 403 si non autorisé
            }

            // Mise à jour des champs de la location
            existingRental.setName(rentalDtoGet.getName());
            existingRental.setSurface(rentalDtoGet.getSurface());
            existingRental.setPrice(rentalDtoGet.getPrice());
            existingRental.setDescription(rentalDtoGet.getDescription());

            saveRental(existingRental); // Enregistrement de la location mise à jour
            return ResponseEntity.ok(new RentalDtoUpdate("Rental updated!")); // Retour de la réponse de mise à jour
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RentalDtoUpdate("Rental not found.")); // Retour d'une réponse 404 si non trouvée
        }
    }

    /*
     * Méthode pour sauvegarder une image.
     * Prend en entrée un fichier image et le chargeur de ressources.
     * Sauvegarde l'image dans le système de fichiers et retourne l'URL de l'image.
     */
    private String saveImage(MultipartFile image, ResourceLoader resourceLoader) {
        try {
            if (image.isEmpty()) {
                throw new RuntimeException("Le fichier image est vide");
            }

            String imageName = image.getOriginalFilename(); // Récupération du nom de l'image
            String contentType = image.getContentType(); // Récupération du type de contenu
            if (!contentType.startsWith("image/")) {
                throw new RuntimeException("Le fichier n'est pas une image");
            }

            Path path = resourceLoader.getResource("classpath:static/images/").getFile().toPath().resolve(imageName); // Définition du chemin de l'image
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent()); // Création des répertoires si nécessaires
            }
            Files.write(path, image.getBytes()); // Écriture de l'image dans le système de fichiers

            return baseUrl + "/images/" + imageName; // Retour de l'URL de l'image
        } catch (IOException e) {
            throw new RuntimeException("Échec de l'enregistrement de l'image", e); // Gestion des exceptions d'entrée/sortie
        }
    }
}
