package com.p3_oc_fs.chatop.services;

import com.p3_oc_fs.chatop.models.Rental;
import com.p3_oc_fs.chatop.repositorys.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    public void saveRentalWithImage(Rental rental, MultipartFile image) {
        String imageName = saveImage(image);
        rental.setPicture(imageName);
        rentalRepository.save(rental);
    }

    private String saveImage(MultipartFile image) {
        try {
            byte[] bytes = image.getBytes();
            Path path = Paths.get("images/" + image.getOriginalFilename());
            Files.write(path, bytes);
            return path.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }
}