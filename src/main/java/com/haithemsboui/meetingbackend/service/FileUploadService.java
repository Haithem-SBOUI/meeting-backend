package com.haithemsboui.meetingbackend.service;

import com.haithemsboui.meetingbackend.core.exception.type.FileNotSelectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    @Value("${profile.images.upload.directory}")
    private String profileImagesUploadDirectory;

    public String uploadFile(MultipartFile file) throws IOException {
        // Check if the file is not empty
        if (file.isEmpty()) {
            throw new FileNotSelectedException("Please select a profile image to upload");
        }
        // Get the original filename
        String originalFilename = file.getOriginalFilename();
        // Create the upload directory if it doesn't exist
        File directory = new File(profileImagesUploadDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        // Save the file to the specified directory
        Path filePath = Path.of(profileImagesUploadDirectory, originalFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        // For demonstration purposes, you could save the file path to the user's profile in the database
        return filePath.toString();
    }


    public String uploadImage(MultipartFile file) throws IOException {
        // Validate file type
        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Invalid file type. Only images are allowed.");
        }
        // Create the upload directory if it doesn't exist
        File directory = new File(profileImagesUploadDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        // Generate a unique filename to avoid overwriting existing files
        String uniqueFilename = generateUniqueFilename(file.getOriginalFilename());
        // Save the file to the specified directory
        Path filePath = Path.of(profileImagesUploadDirectory, uniqueFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "uploads/user_profile/" + uniqueFilename;
    }


    private String generateUniqueFilename(String originalFilename) {
        return System.currentTimeMillis() + "_" + originalFilename;
    }

}
