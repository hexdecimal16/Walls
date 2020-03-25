package com.dhairytripathi.walls.Model;

public class Pixbay {

    private String id;
    private String tags;
    private String previewURL;
    private String previewWidth;
    private String previewHeight;
    private String webformatURL;
    private String webformatWidth;
    private String webformatHeight;
    private String largeImageURL;
    private String fullHDURL;
    private String imageURL;
    private String imageWidth;
    private String imageHeight;
    private String imageSize;
    private String views;
    private String downloads;
    private String favorites;
    private String likes;
    private String comments;
    private String user_id;
    private String user;
    private String userImageURL;

    public Pixbay(String id, String tags, String previewURL, String previewWidth, String previewHeight, String webformatURL, String webformatWidth, String webformatHeight, String largeImageURL, String fullHDURL, String imageURL, String imageWidth, String imageHeight, String imageSize, String views, String downloads, String favorites, String comments, String likes, String user_id, String user, String userImageURL) {
        this.id = id;
        this.tags = tags;
        this.previewURL = previewURL;
        this.previewWidth = previewWidth;
        this.previewHeight = previewHeight;
        this.webformatURL = webformatURL;
        this.webformatWidth = webformatWidth;
        this.webformatHeight = webformatHeight;
        this.largeImageURL = largeImageURL;
        this.fullHDURL = fullHDURL;
        this.imageURL = imageURL;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imageSize = imageSize;
        this.views = views;
        this.downloads = downloads;
        this.favorites = favorites;
        this.likes = likes;
        this.comments = comments;
        this.user_id = user_id;
        this.user = user;
        this.userImageURL = userImageURL;
    }

    public String getComments() {
        return comments;
    }

    public String getId() {
        return id;
    }

    public String getTags() {
        return tags;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public String getPreviewWidth() {
        return previewWidth;
    }

    public String getPreviewHeight() {
        return previewHeight;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public String getWebformatWidth() {
        return webformatWidth;
    }

    public String getWebformatHeight() {
        return webformatHeight;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public String getFullHDURL() {
        return fullHDURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public String getImageSize() {
        return imageSize;
    }

    public String getViews() {
        return views;
    }

    public String getDownloads() {
        return downloads;
    }

    public String getFavorites() {
        return favorites;
    }

    public String getLikes() {
        return likes;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser() {
        return user;
    }

    public String getUserImageURL() {
        return userImageURL;
    }
}
