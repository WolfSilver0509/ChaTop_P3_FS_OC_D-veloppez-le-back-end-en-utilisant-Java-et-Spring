///* Ce package contient les contrôleurs pour la gestion des locations */
package com.p3_oc_fs.chatop.controllers;

import com.p3_oc_fs.chatop.dtos.CreateRentalResponseDto;
import com.p3_oc_fs.chatop.dtos.GetAllRentalsResponseDto;
import com.p3_oc_fs.chatop.dtos.RentalDtoCreate;
import com.p3_oc_fs.chatop.dtos.RentalDtoGet;
import com.p3_oc_fs.chatop.dtos.RentalDtoUpdate;
import com.p3_oc_fs.chatop.models.Rental;
import com.p3_oc_fs.chatop.models.User;
import com.p3_oc_fs.chatop.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @PostMapping("/rentals")
    public ResponseEntity<CreateRentalResponseDto> createRental(@RequestParam("picture") MultipartFile picture,
                                                                @ModelAttribute RentalDtoCreate rentalDto,
                                                                Principal principal) {
        // Obtenir l'utilisateur actuel
        User currentUser = (User) ((Authentication) principal).getPrincipal();
        // Créer une nouvelle location
        Rental rental = new Rental(
                rentalDto.getName(),
                rentalDto.getSurface(),
                rentalDto.getPrice(),
                rentalDto.getDescription(),
                currentUser
        );

        // Sauvegarder la location avec l'image
        rentalService.saveRentalWithImage(rental, picture);

        // Réponse de réussite
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateRentalResponseDto("Rental created!"));
    }


     /* Obtenir toutes les locations */
    @GetMapping("/rentals")
    public ResponseEntity<GetAllRentalsResponseDto> getAllRentals() {
        // Récupérer toutes les locations
        List<Rental> rentals = rentalService.findAllRentals();

        // Convertir les locations en objets RentalDto
        List<RentalDtoGet> rentalDtos = rentals.stream()
                .map(this::convertToRentalDto)
                .collect(Collectors.toList());

        // Créer GetAllRentalsResponseDto et retourner
        return ResponseEntity.ok(new GetAllRentalsResponseDto(rentalDtos));
    }

    // Méthode pour convertir un objet Rental en RentalDto
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

    /* Obtenir une location par son ID */
    @GetMapping("/rentals/{id}")
    public ResponseEntity<RentalDtoGet> getRentalById(@PathVariable Integer id) {
        Optional<Rental> rentalOptional = rentalService.findById(id);
        if (rentalOptional.isPresent()) {
            Rental rental = rentalOptional.get();
            RentalDtoGet rentalDto = convertToRentalDto(rental);
            return ResponseEntity.ok(rentalDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


//    /* Mettre à jour une location */
@PutMapping("/rentals/{id}")
public ResponseEntity<RentalDtoUpdate> updateRental(@PathVariable Integer id,
                                                            @ModelAttribute RentalDtoGet rentalDtoGet,
                                                            Principal principal) {
    Optional<Rental> existingRentalOptional = rentalService.findById(id);
    if (existingRentalOptional.isPresent()) {
        Rental existingRental = existingRentalOptional.get();
        // Vérifier si l'utilisateur est autorisé à mettre à jour cette location
        if (!existingRental.getOwner_id().getUsername().equals(principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new RentalDtoUpdate("You don't have permission to update this rental."));
        }

        // Mettre à jour les informations de la location
        existingRental.setName(rentalDtoGet.getName());
        existingRental.setSurface(rentalDtoGet.getSurface());
        existingRental.setPrice(rentalDtoGet.getPrice());
        existingRental.setDescription(rentalDtoGet.getDescription());

        // Sauvegarder la location mise à jour
        rentalService.saveRental(existingRental);

        // Réponse de réussite
        return ResponseEntity.ok(new RentalDtoUpdate("Rental updated!"));
    } else {
        // Location non trouvée
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new RentalDtoUpdate("Rental not found."));
    }
}

}

