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
@RequestMapping("api/rentals")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserService userService;

    @PostMapping("/1")
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
    @GetMapping
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
        return new RentalDto(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getDescription(),
                rental.getOwner_id().getId(),
                rental.getCreated_at(),
                rental.getUpdated_at()

        );
    }


    @GetMapping("/{id}") // Use path variable for ID
    public ResponseEntity<Rental> getRentalById(@PathVariable Integer id) {
        Optional<Rental> rentalOptional = rentalService.findById(id);
        if (rentalOptional.isPresent()) {
            return ResponseEntity.ok(rentalOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public String test(@ModelAttribute RentalDto string, Model model) {
        return string.toString();
    }

}