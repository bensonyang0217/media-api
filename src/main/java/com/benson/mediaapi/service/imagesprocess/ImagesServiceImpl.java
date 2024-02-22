package com.benson.mediaapi.service.imagesprocess;

import com.benson.mediaapi.controller.ImagesInfoController;
import com.benson.mediaapi.model.ImageInfo;
import com.benson.mediaapi.repository.ImageInfoRepository;
import com.benson.mediaapi.utils.AuthUtils;
import com.benson.mediaapi.vo.RespImageInfoVO;
import com.google.api.client.util.DateTime;
import com.google.cloud.storage.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImagesServiceImpl implements ImagesService{
    private static final Logger logger = LoggerFactory.getLogger(ImagesServiceImpl.class);

    @Value("${spring.cloud.gcp.project-id}")
    private String gcpProjectId;

    @Value("${gcp.storage.bucket-name}")
    private String gcpBucket;

    @Value("${images.url}")
    private String imagesUrl;

    @Autowired
    private ImageInfoRepository imageInfoRepository;

    @Override
    public String upload(MultipartFile image) {
        if (image.isEmpty()) {
            throw new RuntimeException("無法存儲空檔案");
        }

        String _filename = image.getOriginalFilename();
        String suffixName = _filename.substring(_filename.lastIndexOf("."));
        String filename = UUID.randomUUID() + suffixName;
        uploadToGCS(image, filename);
        createImageInfo(_filename, image.getSize(), filename);
        return filename;
    }

    @Override
    public List<RespImageInfoVO> imagesInfoList() {
        String baseImgUrl = imagesUrl;
        int userId = AuthUtils.getUserId();
        List<ImageInfo> imageInfoList = imageInfoRepository.findByUserIdOrderByUploadDateDesc((long) userId);
        List<RespImageInfoVO> respList = imageInfoList.stream()
                .map(imageInfo -> {
                    RespImageInfoVO respImageInfoVO =  new RespImageInfoVO();
                    BeanUtils.copyProperties(imageInfo, respImageInfoVO);
                    String uri = UriComponentsBuilder
                            .fromUriString(baseImgUrl) // 基礎URL
                            .path("/api/images/read-images") // 添加路徑
                            .queryParam("file_name", imageInfo.getThumbnailUrl()) // 添加查詢參數
                            .toUriString(); // 轉換為字符串
                    respImageInfoVO.setThumbnailUrl(uri);
                    return  respImageInfoVO;
                }).collect(Collectors.toList());

        return respList;
    }

    @Override
    public ImageInfo updateImageStatus(String fileName, int size) {
        ImageInfo imageInfo = imageInfoRepository.findBythumbnailUrl(fileName);
        imageInfo.setThumbnailStatus(true);
        imageInfo.setScalingSize(size);
        return imageInfoRepository.save(imageInfo);
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

    private ImageInfo createImageInfo(String filename, Long size, String url){
        Long userId = (long) AuthUtils.getUserId();
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setFileName(filename);
        imageInfo.setFileSize(size);
        imageInfo.setThumbnailUrl(url);
        imageInfo.setUploadDate(LocalDateTime.now(ZoneId.of("Asia/Taipei")));
        imageInfo.setUserId(userId);
        imageInfo.setThumbnailStatus(false);
        return imageInfoRepository.save(imageInfo);
    }
}
