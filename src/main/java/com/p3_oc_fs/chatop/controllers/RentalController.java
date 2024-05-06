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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/rentals")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserService userService;

    @PostMapping("/1")
    public ResponseEntity<String> createRental(@RequestParam("picture") MultipartFile picture,
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
        return ResponseEntity.ok("Rental created successfully !");
    }

    @GetMapping // Use GET for retrieving data
    public ResponseEntity<List<Rental>> getAllRentals() {
        List<Rental> rentals = rentalService.findAllRentals();
        return ResponseEntity.ok(rentals);
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

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRental(@PathVariable Integer id,
                                               @ModelAttribute RentalDto rentalDto,
                                               Principal principal) {
        Optional<Rental> existingRentalOptional = rentalService.findById(id);
        if (existingRentalOptional.isPresent()) {
            Rental existingRental = existingRentalOptional.get();
            if (!existingRental.getOwner().getUsername().equals(principal.getName())) {
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



//    @PutMapping("/{id}")
//    public ResponseEntity<Rental> updateRental(@PathVariable Integer id,
//                                               @ModelAttribute RentalDto rentalDto) {
//        Optional<Rental> existingRentalOptional = rentalService.findById(id);
//        if (existingRentalOptional.isPresent()) {
//            Rental existingRental = existingRentalOptional.get();
//            existingRental.setName(rentalDto.getName());
//            existingRental.setSurface(rentalDto.getSurface());
//            existingRental.setPrice(rentalDto.getPrice());
//            existingRental.setDescription(rentalDto.getDescription());
//
//            Rental updatedRental = rentalService.saveRental(existingRental); // Use saveRental for updates
//            return ResponseEntity.ok(updatedRental);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }


}


