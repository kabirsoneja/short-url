package com.ks.shorturl.model;

import java.time.LocalDateTime;

public class Url {

    private String shortUrl;
    private String url;
    private LocalDateTime createdAt;


    public Url(String shortUrl, String longUrl, LocalDateTime createdAt) {
        this.shortUrl = shortUrl;
        this.url = longUrl;
        this.createdAt = createdAt;
    }

    public Url(){
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
