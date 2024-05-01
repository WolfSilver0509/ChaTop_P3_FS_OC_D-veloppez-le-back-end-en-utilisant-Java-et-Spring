package com.p3_oc_fs.chatop.controllers;

import com.p3_oc_fs.chatop.dtos.RentalDto;
import com.p3_oc_fs.chatop.models.Rental;
import com.p3_oc_fs.chatop.models.User;
import com.p3_oc_fs.chatop.services.RentalService;
import com.p3_oc_fs.chatop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@PostMapping
public ResponseEntity<Rental> createRental(@RequestParam("picture") MultipartFile picture, @RequestBody RentalDto rentalDto, @RequestParam("ownerId") Integer ownerId) {
    Optional<User> ownerOptional = userService.findById(ownerId);
    if (ownerOptional.isPresent()) {
        User owner = ownerOptional.get();
        Rental rental = new Rental();
        rental.setName(rentalDto.getName());
        rental.setSurface(rentalDto.getSurface());
        rental.setPrice(rentalDto.getPrice());
        rental.setDescription(rentalDto.getDescription());
        rental.setOwner(owner);
        rentalService.saveRentalWithImage(rental, picture);
        return ResponseEntity.ok(rental);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

}