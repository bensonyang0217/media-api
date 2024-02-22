package com.benson.mediaapi.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;


public class RespImageInfoVO implements Serializable {
    private Long id;
    private String fileName;
    private long fileSize;

    private long scalingSize;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uploadDate;
    private boolean thumbnailStatus;
    private String thumbnailUrl;
    private Long userId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public boolean isThumbnailStatus() {
        return thumbnailStatus;
    }

    public void setThumbnailStatus(boolean thumbnailStatus) {
        this.thumbnailStatus = thumbnailStatus;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getScalingSize() {
        return scalingSize;
    }

    public void setScalingSize(long scalingSize) {
        this.scalingSize = scalingSize;
    }

}
