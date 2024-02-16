package com.benson.mediaapi.service.imagesprocess;

import org.springframework.web.multipart.MultipartFile;

public interface ImagesService {
    String upload(MultipartFile image);
}
