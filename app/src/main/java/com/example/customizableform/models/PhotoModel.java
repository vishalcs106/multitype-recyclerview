package com.example.customizableform.models;

import com.example.customizableform.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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

    public JSONObject getDataMap(){
        JSONObject dataMap = new JSONObject();
        try {
            dataMap.put(Constants.KEY_PUBLIC_ID, url);
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
             jsonObject.remove(Constants.KEY_URL);
             jsonObject.put(Constants.KEY_DATA_MAP, getDataMap());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
