package com.example.customizableform.models;

public class PhotoModel extends BaseFormItemModel {
    private String url;

    public PhotoModel(String id, String itemType, String title) {
        super(id, itemType, title);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
