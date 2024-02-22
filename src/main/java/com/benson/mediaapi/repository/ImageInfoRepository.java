package com.benson.mediaapi.repository;

import com.benson.mediaapi.model.ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageInfoRepository extends JpaRepository<ImageInfo, Long> {
    ImageInfo findByFileName(String fileName);
    List<ImageInfo> findByUserId(Long userId);
    List<ImageInfo> findByUserIdOrderByUploadDateDesc(Long userId);
}
