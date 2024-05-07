package com.p3_oc_fs.chatop.controllers;

import com.p3_oc_fs.chatop.dtos.RentalDtoCreate;
import com.p3_oc_fs.chatop.dtos.RentalDtoGet;
import com.p3_oc_fs.chatop.models.Rental;
import com.p3_oc_fs.chatop.models.User;
import com.p3_oc_fs.chatop.services.RentalService;
import com.p3_oc_fs.chatop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserService userService;

    @PostMapping("/rentals")
    public ResponseEntity<Rental> createRental(@RequestParam("picture") MultipartFile picture,
                                               @ModelAttribute RentalDtoCreate rentalDto,
                                               Principal principal) {
        User currentUser = (User) ((Authentication) principal).getPrincipal();
        Rental rental = new Rental(
                rentalDto.getName(),
                rentalDto.getSurface(),
                rentalDto.getPrice(),
                rentalDto.getDescription(),
                currentUser
        );

        rentalService.saveRentalWithImage(rental, picture);
        return ResponseEntity.ok(rental);
    }

    @GetMapping("/rentals")
    public ResponseEntity<Map<String, List<RentalDtoGet>>> getAllRentals() {
        List<Rental> rentals = rentalService.findAllRentals();

        // Convertir les locations en objets RentalDto
        List<RentalDtoGet> rentalDtos = rentals.stream()
                .map(this::convertToRentalDto)
                .collect(Collectors.toList());

        // Créer un objet Map pour envelopper les locations dans la clé "rentals"
        Map<String, List<RentalDtoGet>> response = new HashMap<>();
        response.put("rentals", rentalDtos);

        return ResponseEntity.ok(response);

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


    @PutMapping("/rentals/{id}")
    public ResponseEntity<String> updateRental(@PathVariable Integer id,
                                               @ModelAttribute RentalDtoGet rentalDtoGet,
                                               Principal principal) {
        Optional<Rental> existingRentalOptional = rentalService.findById(id);
        if (existingRentalOptional.isPresent()) {
            Rental existingRental = existingRentalOptional.get();
            if (!existingRental.getOwner_id().getUsername().equals(principal.getName())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update this rental.");
            }

            existingRental.setName(rentalDtoGet.getName());
            existingRental.setSurface(rentalDtoGet.getSurface());
            existingRental.setPrice(rentalDtoGet.getPrice());
            existingRental.setDescription(rentalDtoGet.getDescription());

            rentalService.saveRental(existingRental);

            return ResponseEntity.ok("Rental updated successfully !");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental not found.");
        }

    }

}