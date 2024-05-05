package com.p3_oc_fs.chatop.services;

import com.p3_oc_fs.chatop.models.Rental;
import com.p3_oc_fs.chatop.repositorys.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class RentalService {

    @Value("${app.base-url}")
    private String baseUrl;

    @Autowired
    private RentalRepository rentalRepository;

    public void saveRentalWithImage(Rental rental, MultipartFile image) {
        String imageUrl = saveImage(image);
        rental.setPicture(imageUrl);
        rentalRepository.save(rental);
    }


    private String saveImage(MultipartFile image) {
        try {
            byte[] bytes = image.getBytes();
            String imageName = image.getOriginalFilename();
            String projectDir = System.getProperty("user.dir");
            Path path = Paths.get(projectDir, "/src/main/resources/static/images/", imageName);
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, bytes);
            return baseUrl + "/images/" + imageName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }

//    private String saveImage(MultipartFile image) {
//        try {
//            byte[] bytes = image.getBytes();
//            String imageName = image.getOriginalFilename();
//            Path path = Paths.get(".../.../.../resources/static/images/" + imageName);
//            if (!Files.exists(path.getParent())) {
//                Files.createDirectories(path.getParent());
//            }
//            Files.write(path, bytes);
//            return "/images/" + imageName;
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to store image", e);
//        }
//    }
}
