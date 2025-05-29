package vn.edu.volunteer.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.volunteer.service.FileStorageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    
    private final Path fileStorageLocation;

    public FileStorageServiceImpl() {
        this.fileStorageLocation = Paths.get("uploads")
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileExtension = "";
            
            int lastIndex = fileName.lastIndexOf('.');
            if (lastIndex > 0) {
                fileExtension = fileName.substring(lastIndex);
            }

            // Generate unique file name
            String newFileName = UUID.randomUUID().toString() + fileExtension;
            
            Path targetLocation = this.fileStorageLocation.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return newFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to store file.", ex);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.deleteIfExists(targetLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to delete file.", ex);
        }
    }

    @Override
    public byte[] loadFile(String fileName) {
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            return Files.readAllBytes(targetLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load file.", ex);
        }
    }

    @Override
    public String getFileUrl(String fileName) {
        return "/uploads/" + fileName;
    }
} 