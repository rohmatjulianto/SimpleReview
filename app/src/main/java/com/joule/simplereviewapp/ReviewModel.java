package com.joule.simplereviewapp;

import org.json.JSONObject;

public class ReviewModel {
    String id;
    String ratingbar;
    String name;
    String content;
    String image;
    String date;

    public ReviewModel(String id, String ratingbar, String name, String content, String image, String date) {
        this.id = id;
        this.ratingbar = ratingbar;
        this.name = name;
        this.content = content;
        this.image = image;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public ReviewModel(String id, String ratingbar, String name, String content, String image) {
        this.id = id;
        this.ratingbar = ratingbar;
        this.name = name;
        this.content = content;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRatingbar() {
        return ratingbar;
    }

    public void setRatingbar(String ratingbar) {
        this.ratingbar = ratingbar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
