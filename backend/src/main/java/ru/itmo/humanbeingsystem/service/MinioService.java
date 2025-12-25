package ru.itmo.humanbeingsystem.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MinioService {
  @Autowired private MinioClient minioClient;

  @Value("${minio.bucket}")
  private String bucket;

  public String uploadFile(MultipartFile file) {
    try {
      String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
      minioClient.putObject(
          PutObjectArgs.builder().bucket(bucket).object(fileName).stream(
                  file.getInputStream(), file.getSize(), -1)
              .contentType(file.getContentType())
              .build());
      return fileName;
    } catch (Exception e) {
      throw new RuntimeException("MinIO upload failed", e);
    }
  }

  public InputStream downloadFile(String fileName) {
    try {
      return minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(fileName).build());
    } catch (Exception e) {
      throw new RuntimeException("MinIO download failed", e);
    }
  }

  public void deleteFile(String fileName) {
    try {
      minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(fileName).build());
    } catch (Exception e) {
      throw new RuntimeException("MinIO delete failed", e);
    }
  }
}
