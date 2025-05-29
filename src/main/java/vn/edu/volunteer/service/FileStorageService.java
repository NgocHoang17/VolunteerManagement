package vn.edu.volunteer.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile file);
    void deleteFile(String fileName);
    byte[] loadFile(String fileName);
    String getFileUrl(String fileName);
} 