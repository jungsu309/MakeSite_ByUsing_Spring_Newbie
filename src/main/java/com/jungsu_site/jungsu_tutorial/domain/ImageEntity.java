package com.jungsu_site.jungsu_tutorial.domain;

import java.time.LocalDateTime;

public class ImageEntity {

    //일단 이정도만.
    private String fileName;
    private String filePath;
    private long fileSize;
    private String userName;
    private LocalDateTime currentTime; // 현재 시간 추가

    public ImageEntity() {
        this.currentTime = LocalDateTime.now();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }
    public void setCurrentTime (LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

}
