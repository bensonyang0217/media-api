package com.benson.mediaapi.vo;

import java.io.Serializable;

public class CropImageRequestVO implements Serializable {
    private String srcFileName;
    private int targetWidth;
    private int targetHeight;

    // Getters and Setters
    public String getSrcFileName() {
        return srcFileName;
    }

    public void setSrcFileName(String srcFileName) {
        this.srcFileName = srcFileName;
    }

    public int getTargetWidth() {
        return targetWidth;
    }

    public void setTargetWidth(int targetWidth) {
        this.targetWidth = targetWidth;
    }

    public int getTargetHeight() {
        return targetHeight;
    }

    public void setTargetHeight(int targetHeight) {
        this.targetHeight = targetHeight;
    }
}

