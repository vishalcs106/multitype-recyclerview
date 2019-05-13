package com.example.customizableform.models;

import com.example.customizableform.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class CommentModel extends BaseFormItemModel{
    private boolean isCommentOn;
    private String comment;

    public CommentModel(String id, String itemType, String title) {
        super(id, itemType, title);
    }

    public boolean isCommentOn() {
        return isCommentOn;
    }

    public void setCommentOn(boolean commentOn) {
        isCommentOn = commentOn;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public JSONObject getDataMap(){
        JSONObject dataMap = new JSONObject();
        try {
            dataMap.put(Constants.KEY_IS_COMMENT_ON, isCommentOn);
            dataMap.put(Constants.KEY_COMMENT, comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataMap;
    }


    public JSONObject getJson() {
        Gson gson = new Gson();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(this));
            jsonObject.remove(Constants.KEY_COMMENT);
            jsonObject.remove(Constants.KEY_IS_COMMENT_ON);
            jsonObject.put(Constants.KEY_DATA_MAP, getDataMap());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
