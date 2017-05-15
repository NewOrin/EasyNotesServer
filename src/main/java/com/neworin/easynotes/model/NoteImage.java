package com.neworin.easynotes.model;

public class NoteImage {

    public NoteImage() {
    }

    public NoteImage(Long userId, String imageName) {
        this.userId = userId;
        this.imageName = imageName;
    }

    private String imageName;

    private Long userId;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName == null ? null : imageName.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}