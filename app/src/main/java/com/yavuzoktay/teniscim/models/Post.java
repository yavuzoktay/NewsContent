package com.yavuzoktay.teniscim.models;


import java.util.ArrayList;
import java.util.List;



public class Post {

    private String title;
    private String link;
    private String description;
    private String imageUrl;
    private List<String> category = new ArrayList<String>();
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public void addCategoryItem(String categoryItem) {
        this.category.add(categoryItem);
    }


}