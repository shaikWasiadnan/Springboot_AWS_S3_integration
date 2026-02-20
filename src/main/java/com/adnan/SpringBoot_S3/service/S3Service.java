package com.adnan.SpringBoot_S3.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {
    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Value("${aws.region}")
    private String regionString;
    //used for testing locally
//    @Value("${aws.access-key}")
//    private String accessKey;
//    @Value("${aws.secret-key}")
//    private String secretKey;

    private S3Client getClient(){
        //used for testing locally
//        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey,secretKey);
        return S3Client.builder().
                region(Region.of(regionString))
//                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
    public String uploadImage(MultipartFile file) throws IOException{
        String uniqueFileName = UUID.randomUUID()+"-"+file.getOriginalFilename();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(uniqueFileName)
                .contentType(file.getContentType())
                .build();
        getClient().putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, regionString, uniqueFileName);
    }
}
