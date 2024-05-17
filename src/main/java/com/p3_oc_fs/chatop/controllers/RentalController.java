///* Ce package contient les contrôleurs pour la gestion des locations */
        package com.p3_oc_fs.chatop.controllers;

import com.p3_oc_fs.chatop.dtos.CreateRentalResponseDto; // Importation du DTO pour la réponse de création de location
import com.p3_oc_fs.chatop.dtos.GetAllRentalsResponseDto; // Importation du DTO pour la réponse de récupération de toutes les locations
import com.p3_oc_fs.chatop.dtos.RentalDtoCreate; // Importation du DTO pour la création de location
import com.p3_oc_fs.chatop.dtos.RentalDtoGet; // Importation du DTO pour la récupération de location par ID
import com.p3_oc_fs.chatop.dtos.RentalDtoUpdate; // Importation du DTO pour la mise à jour de location
import com.p3_oc_fs.chatop.services.RentalService; // Importation du service des locations
import org.springframework.beans.factory.annotation.Autowired; // Importation de l'annotation Autowired pour l'injection de dépendances
import org.springframework.http.ResponseEntity; // Importation de la classe ResponseEntity pour les réponses HTTP
import org.springframework.web.bind.annotation.*; // Importation des annotations pour les contrôleurs Spring
import org.springframework.web.multipart.MultipartFile; // Importation de la classe MultipartFile pour la gestion des fichiers

import java.security.Principal; // Importation de la classe Principal pour gérer les informations d'utilisateur

@RestController // Indique que cette classe est un contrôleur REST
@RequestMapping("api") // Indique le chemin de base pour les requêtes HTTP
public class RentalController {

    @Autowired // Injection de dépendance pour RentalService
    private RentalService rentalService;

    /*
     * Point de terminaison pour créer une nouvelle location.
     * Prend en entrée un fichier image, un DTO de création de location, et les informations de l'utilisateur.
     * Retourne une réponse contenant le DTO de la réponse de création de location.
     */
    @PostMapping("/rentals")
    public ResponseEntity<CreateRentalResponseDto> createRental(@RequestParam("picture") MultipartFile picture,
                                                                @ModelAttribute RentalDtoCreate rentalDto,
                                                                Principal principal) {
        return rentalService.createRental(picture, rentalDto, principal);
    }

    /*
     * Point de terminaison pour récupérer toutes les locations.
     * Retourne une réponse contenant le DTO de la réponse de récupération de toutes les locations.
     */
    @GetMapping("/rentals")
    public ResponseEntity<GetAllRentalsResponseDto> getAllRentals() {
        return rentalService.getAllRentals();
    }

    /*
     * Point de terminaison pour récupérer une location par son ID.
     * Prend en entrée l'ID de la location.
     * Retourne une réponse contenant le DTO de la location récupérée.
     */
    @GetMapping("/rentals/{id}")
    public ResponseEntity<RentalDtoGet> getRentalById(@PathVariable Integer id) {
        return rentalService.getRentalById(id);
    }

    /*
     * Point de terminaison pour mettre à jour une location existante.
     * Prend en entrée l'ID de la location, un DTO de la location à mettre à jour, et les informations de l'utilisateur.
     * Retourne une réponse contenant le DTO de la location mise à jour.
     */
    @PutMapping("/rentals/{id}")
    public ResponseEntity<RentalDtoUpdate> updateRental(@PathVariable Integer id,
                                                        @ModelAttribute RentalDtoGet rentalDtoGet,
                                                        Principal principal) {
        return rentalService.updateRental(id, rentalDtoGet, principal);
    }
}
