package com.benson.mediaapi.service.imagesprocess;

import com.benson.mediaapi.controller.ImagesInfoController;
import com.google.cloud.storage.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImagesServiceImpl implements ImagesService{
    private static final Logger logger = LoggerFactory.getLogger(ImagesServiceImpl.class);

    @Value("${spring.cloud.gcp.project-id}")
    private String gcpProjectId;

    @Value("${gcp.storage.bucket-name}")
    private String gcpBucket;
    @Override
    public String upload(MultipartFile image) {
        if (image.isEmpty()) {
            throw new RuntimeException("無法存儲空檔案");
        }

        String filename = image.getOriginalFilename();
        String suffixName = filename.substring(filename.lastIndexOf("."));
        filename = UUID.randomUUID() + suffixName;
        uploadToGCS(image, filename);

        return filename;
    }

    private void uploadToGCS(MultipartFile image, String fileName){
        String resultPath = null;
        try {
            // get Storage instance
            Storage storage = StorageOptions.newBuilder()
                    .setProjectId(gcpProjectId)
                    .build()
                    .getService();

            // Bucket name and folder
            String bucketName = gcpBucket;
            Bucket bucket = storage.get(bucketName);
            for(Blob blob : bucket.list().iterateAll()){
                logger.info("bucket item:"+ blob.getName());
            }
//            String fileName = image.getOriginalFilename(); // 獲取原始文件名
            // Blob 信息
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName).build();


            // 上傳文件
            Blob blob = storage.create(blobInfo, image.getBytes());

            // 獲取文件 URL
            resultPath = blob.getMediaLink();
            logger.info("Uploaded file link: " + resultPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
