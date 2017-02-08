package com.example.v5188.NewsFlash.json;


import java.util.List;

public class NewsList {
    String imgsrc;
    String title;
    String digest;
    String url;
    String docid;
    List<NewsPicture> ads;
    List<NewThreePictures> imgextra;

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<NewThreePictures> getImgextra() {
        return imgextra;
    }

    public void setImgextra(List<NewThreePictures> imgextra) {
        this.imgextra = imgextra;
    }

    public List<NewsPicture> getAds() {
        return ads;
    }

    public void setAds(List<NewsPicture> ads) {
        this.ads = ads;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }


}
