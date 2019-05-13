package com.example.customizableform;

import android.app.Application;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

public class App extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        initCloudinaryClient();
    }

    private void initCloudinaryClient() {
        Map config = new HashMap();
        config.put("cloud_name", "db7iorevu");
        config.put("api_key", "912317465196511");
        config.put("api_secret", "SOVvGilE_EGtmJaK4WGCKGgNOak");
        MediaManager.init(this, config);
    }

}
