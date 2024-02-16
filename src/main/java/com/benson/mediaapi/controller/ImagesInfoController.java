package com.benson.mediaapi.controller;
import com.benson.mediaapi.service.imagesprocess.ImagesService;
import io.cloudevents.CloudEvent;
import io.cloudevents.rw.CloudEventRWException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.cloudevents.spring.http.CloudEventHttpUtils;

@RestController
@RequestMapping("/api/images")
public class ImagesInfoController {
    private static final Logger logger = LoggerFactory.getLogger(ImagesInfoController.class);

    @Autowired
    private ImagesService imagesService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile image) {
        String respImages = imagesService.upload(image);
        return ResponseEntity.ok(respImages);
    }

    @PostMapping("/scaling")
    public ResponseEntity<String> handleImageUploadEvent(@RequestBody String body, @RequestHeader HttpHeaders headers) {
        CloudEvent event;
        try {
            event =
                    CloudEventHttpUtils.fromHttp(headers)
                            .withData(headers.getContentType().toString(), body.getBytes())
                            .build();
        } catch (CloudEventRWException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        String ceSubject = event.getSubject();
        String msg = "Detected change in Cloud Storage bucket: " + ceSubject;
        logger.info("---Image processed---");
        logger.info(msg);

        return ResponseEntity.ok("Image processed");
    }
}
