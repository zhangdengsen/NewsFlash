package com.example.v5188.NewsFlash.slidingmenu_picture.details;


import java.util.List;

public class Data {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;
    public List<PicsList> getPics() {
        return pics;
    }

    public void setPics(List<PicsList> pics) {
        this.pics = pics;
    }

    List<PicsList> pics;

}
