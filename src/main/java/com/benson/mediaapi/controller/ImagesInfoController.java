package com.benson.mediaapi.controller;
import com.benson.mediaapi.model.ImageInfo;
import com.benson.mediaapi.service.imagesprocess.ImagesService;
import com.benson.mediaapi.vo.NotificationVO;
import com.benson.mediaapi.vo.RespImageInfoVO;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import io.cloudevents.rw.CloudEventRWException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/images")
public class ImagesInfoController {
    private static final Logger logger = LoggerFactory.getLogger(ImagesInfoController.class);


    @Value("${spring.cloud.gcp.project-id}")
    private String projectId;

    @Value("${target.bucket}")
    private String targetBucket;

    @Autowired
    private ImagesService imagesService;

    @GetMapping("")
    public List<RespImageInfoVO> images(){
        return imagesService.imagesInfoList();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile image) {
        String respImages = imagesService.upload(image);
        return ResponseEntity.ok(respImages);
    }

    @PostMapping("/scaling")
    public ResponseEntity<String> handleImageUploadEvent(@RequestBody NotificationVO body, @RequestHeader HttpHeaders headers) {
        try {
            logger.info("image name: " + body.getMessage().getAttributes().getObjectId());
        } catch (CloudEventRWException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        logger.info("---Image processed---");


        return ResponseEntity.ok("Image processed");
    }
    @GetMapping(value = "read-images")
    public ResponseEntity<byte[]> readGcsFile(@RequestParam("file_name") Optional<String> imageName) throws IOException {
        Storage storage = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .build()
                .getService();
        String name = imageName.orElse("my-file.txt");
        Blob blob = storage.get(BlobId.of(targetBucket,name));
        if (blob == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        byte[] data = blob.getContent();
        HttpHeaders headers = new HttpHeaders();

        Path filePath = Paths.get(name);
        String mimeType = Files.probeContentType(filePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        headers.setContentType(MediaType.parseMediaType(mimeType));

        headers.setContentDispositionFormData("attachment", name);

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    @PatchMapping("status/{id}")
    public Boolean updateImagesStatus(@PathVariable String id){
        ImageInfo imageInfo = imagesService.updateImageStatus(id);
        return imageInfo.isThumbnailStatus();
    }
}
