package com.p3_oc_fs.chatop.controllers;

import com.p3_oc_fs.chatop.dtos.RentalDto;
import com.p3_oc_fs.chatop.models.Rental;
import com.p3_oc_fs.chatop.models.User;
import com.p3_oc_fs.chatop.services.RentalService;
import com.p3_oc_fs.chatop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
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
                                               @ModelAttribute RentalDto rentalDto,
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

//    @GetMapping
//    public ResponseEntity<List<Rental>> getAllRentals() {
//        List<Rental> rentals = rentalService.findAllRentals();
//        return ResponseEntity.ok(rentals);
//    }

    //    @GetMapping
//    public ResponseEntity<List<RentalDto>> getAllRentals() {
//        List<Rental> rentals = rentalService.findAllRentals();
//
//        // Convertir les locations en objets RentalDto
//        List<RentalDto> rentalDtos = rentals.stream()
//                .map(this::convertToRentalDto)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(rentalDtos);
//    }
//
//    // Méthode pour convertir un objet Rental en RentalDto
//    private RentalDto convertToRentalDto(Rental rental) {
//        return new RentalDto(
//                rental.getId(),
//                rental.getName(),
//                rental.getSurface(),
//                rental.getPrice(),
//                rental.getDescription(),
//                rental.getOwner_id().getId(),
//                rental.getCreated_at(),
//                rental.getUpdated_at()
//
//        );
//    }
    @GetMapping("/rentals")
    public ResponseEntity<Map<String, List<RentalDto>>> getAllRentals() {
        List<Rental> rentals = rentalService.findAllRentals();

        // Convertir les locations en objets RentalDto
        List<RentalDto> rentalDtos = rentals.stream()
                .map(this::convertToRentalDto)
                .collect(Collectors.toList());

        // Créer un objet Map pour envelopper les locations dans la clé "rentals"
        Map<String, List<RentalDto>> response = new HashMap<>();
        response.put("rentals", rentalDtos);

        return ResponseEntity.ok(response);

    }

    // Méthode pour convertir un objet Rental en RentalDto
    private RentalDto convertToRentalDto(Rental rental) {
//        String pictureUrl = rental.getPicture();
        return new RentalDto(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getDescription(),
                rental.getOwner_id().getId(),
                rental.getCreated_at(),
                rental.getUpdated_at(),
                rental.getPicture()
//                pictureUrl

        );
    }

//
//    @GetMapping("/rentals/{id}") // Use path variable for ID
//    public ResponseEntity<Rental> getRentalById(@PathVariable Integer id) {
//        Optional<Rental> rentalOptional = rentalService.findById(id);
//        if (rentalOptional.isPresent()) {
//            return ResponseEntity.ok(rentalOptional.get());
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

    @GetMapping("/rentals/{id}")
    public ResponseEntity<Map<String, RentalDto>> getRentalById(@PathVariable Integer id) {
        Optional<Rental> rentalOptional = rentalService.findById(id);
        if (rentalOptional.isPresent()) {
            Rental rental = rentalOptional.get();
            RentalDto rentalDto = convertToRentalDto(rental);

            // Créer un objet Map pour envelopper les détails de la location dans la clé "rental"
            Map<String, RentalDto> response = new HashMap<>();
            response.put("rental", rentalDto);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/rentals/{id}")
    public ResponseEntity<String> updateRental(@PathVariable Integer id,
                                               @RequestBody RentalDto rentalDto,
                                               Principal principal) {
        Optional<Rental> existingRentalOptional = rentalService.findById(id);
        if (existingRentalOptional.isPresent()) {
            Rental existingRental = existingRentalOptional.get();
            if (!existingRental.getOwner_id().getUsername().equals(principal.getName())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update this rental.");
            }

            existingRental.setName(rentalDto.getName());
            existingRental.setSurface(rentalDto.getSurface());
            existingRental.setPrice(rentalDto.getPrice());
            existingRental.setDescription(rentalDto.getDescription());

            rentalService.saveRental(existingRental);

            return ResponseEntity.ok("Rental updated successfully !");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental not found.");
        }

    }

}