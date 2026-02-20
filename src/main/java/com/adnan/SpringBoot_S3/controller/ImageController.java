package com.adnan.SpringBoot_S3.controller;

import com.adnan.SpringBoot_S3.entity.ImageRecord;
import com.adnan.SpringBoot_S3.repository.ImageRepository;
import com.adnan.SpringBoot_S3.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final S3Service service;
    private final ImageRepository repo;

    public ImageController(S3Service service, ImageRepository repo) {
        this.service = service;
        this.repo = repo;
    }
    @PostMapping
    public ResponseEntity<String> uploadToS3AndSaveToDb(@RequestParam("file") MultipartFile file){
        try{
            String s3Url = service.uploadImage(file);
            ImageRecord record = new ImageRecord();
            record.setOriginalFileName(file.getOriginalFilename());
            record.setS3Url(s3Url);
            repo.save(record);
            return ResponseEntity.ok("Success, File URL : "+s3Url);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }
}
