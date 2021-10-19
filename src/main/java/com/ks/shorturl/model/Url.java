package com.ks.shorturl.model;

public class Url {

    private String shortUrl;
    private String url;
    private String timestamp;


    public Url(String shortUrl, String longUrl, String timestamp) {
        this.shortUrl = shortUrl;
        this.url = longUrl;
        this.timestamp = timestamp;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
