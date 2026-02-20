package com.adnan.SpringBoot_S3.repository;

import com.adnan.SpringBoot_S3.entity.ImageRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageRecord,Long> {
}
