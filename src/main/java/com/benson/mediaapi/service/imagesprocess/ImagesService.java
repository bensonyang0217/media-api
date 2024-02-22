package com.benson.mediaapi.service.imagesprocess;

import com.benson.mediaapi.model.ImageInfo;
import com.benson.mediaapi.vo.RespImageInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImagesService {
    String upload(MultipartFile image);

    List<RespImageInfoVO> imagesInfoList();

    ImageInfo updateImageStatus(String fileName, int size);

}
