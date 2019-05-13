package com.example.customizableform.models;

import com.example.customizableform.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SingleChoiceModel extends BaseFormItemModel{
    private String[] options;
    private String selection;

    public SingleChoiceModel(String id, String itemType, String title, String[] options) {
        super(id, itemType, title);
        this.options = options;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public JSONObject getDataMap(){
        JSONObject dataMap = new JSONObject();
        try {
            dataMap.put(Constants.KEY_OPTIONS, new JSONArray(new Gson().toJson(options)));
            dataMap.put(Constants.KEY_SELECTION, selection);
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
            jsonObject.remove(Constants.KEY_OPTIONS);
            jsonObject.remove(Constants.KEY_SELECTION);
            jsonObject.put(Constants.KEY_DATA_MAP, getDataMap());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
