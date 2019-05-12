package com.example.customizableform.models;

public class BaseFormItemModel {

    private String id;
    private String itemType;
    private String title;

    public BaseFormItemModel(String id, String itemType, String title) {
        this.id = id;
        this.itemType = itemType;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
