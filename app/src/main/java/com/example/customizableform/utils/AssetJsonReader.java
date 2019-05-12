package com.example.customizableform.utils;

import android.content.Context;

import com.example.customizableform.models.BaseFormItemModel;
import com.example.customizableform.models.CommentModel;
import com.example.customizableform.models.PhotoModel;
import com.example.customizableform.models.SingleChoiceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AssetJsonReader {

    public List<BaseFormItemModel> readJsonAndReturnList(Context context){
        List<BaseFormItemModel> formItemsList = null;
        try {
            InputStream is = context.getAssets().open("form_items.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            try {
                JSONArray listJson = new JSONArray(json);
                formItemsList = parseJsonForList(listJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return formItemsList;
    }

    private List<BaseFormItemModel> parseJsonForList(JSONArray listJson) {
        List<BaseFormItemModel> baseFormItemModels = new ArrayList<>();
        for(int i = 0; i < listJson.length(); i++){
            JSONObject itemJson = listJson.optJSONObject(i);
            if(itemJson != null){
                switch (itemJson.optString(Constants.KEY_TYPE)){
                    case Constants.TYPE_COMMENT:
                        baseFormItemModels.add(parseCommentModel(itemJson));
                        break;
                    case Constants.TYPE_PHOTO:
                        baseFormItemModels.add(parsePhotoModel(itemJson));
                        break;
                    case Constants.TYPE_SINGLE_CHOICE:
                        baseFormItemModels.add(parseSingleChoiceModel(itemJson));
                        break;
                }
            }
        }
        return baseFormItemModels;
    }

    private SingleChoiceModel parseSingleChoiceModel(JSONObject itemJson) {
        return new SingleChoiceModel(itemJson.optString(Constants.KEY_ID), itemJson.optString(Constants.KEY_TYPE),
                itemJson.optString(Constants.KEY_TITLE), parseOptions(itemJson.optJSONObject(
                        Constants.KEY_DATA_MAP).optJSONArray(Constants.KEY_OPTIONS)));
    }

    private String[] parseOptions(JSONArray jsonArray) {
        String[] stringArray = null;
        if (jsonArray != null) {
           stringArray = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                stringArray[i] = jsonArray.optString(i);
            }
        }
        return stringArray;
    }

    private PhotoModel parsePhotoModel(JSONObject itemJson) {
        return new PhotoModel(itemJson.optString(Constants.KEY_ID), itemJson.optString(Constants.KEY_TYPE),
                itemJson.optString(Constants.KEY_TITLE));
    }

    private CommentModel parseCommentModel(JSONObject itemJson) {
        return new CommentModel(itemJson.optString(Constants.KEY_ID), itemJson.optString(Constants.KEY_TYPE),
                itemJson.optString(Constants.KEY_TITLE));
    }

}
