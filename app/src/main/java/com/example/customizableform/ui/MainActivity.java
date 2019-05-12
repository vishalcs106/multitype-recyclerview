package com.example.customizableform.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.customizableform.R;
import com.example.customizableform.interfaces.ImageUpLoadListener;
import com.example.customizableform.interfaces.OpenGalleryListener;
import com.example.customizableform.models.BaseFormItemModel;
import com.example.customizableform.utils.AssetJsonReader;
import com.example.customizableform.utils.GalleryToCloudinaryHelper;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private int photoClickPosition = 0;
    private FormAdapter adapter;
    private List<BaseFormItemModel> formItemModels = new ArrayList<>();

    private OpenGalleryListener openGalleryListener = new OpenGalleryListener() {
        @Override
        public void openGallery(int position) {
            photoClickPosition = position;
            if(checkPermissionForReadExtertalStorage()) {
                openGallery(position);
            } else {
                requestPermissionForReadExtertalStorage();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        formItemModels = new AssetJsonReader().readJsonAndReturnList(this);
        adapter = new FormAdapter(formItemModels);
        adapter.setOpenGalleryListener(openGalleryListener);
    }

    private void openGallery(){
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, 22);
    }



    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }


    public void requestPermissionForReadExtertalStorage() {
        try {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    10000);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == 22 || requestCode == 11) && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            GalleryToCloudinaryHelper galleryToCloudinaryHelper = new GalleryToCloudinaryHelper();
            try {
                String path = galleryToCloudinaryHelper.getPath(this, uri);
                galleryToCloudinaryHelper.uploadImage(path, new ImageUpLoadListener() {
                    @Override
                    public void uploadSuccess(String imageUrl) {
                        adapter.updateImageAt(photoClickPosition, imageUrl);
                    }

                    @Override
                    public void uploadFailed(String message) {
                        adapter.updateImageAt(photoClickPosition, "");
                    }
                });

            }catch (URISyntaxException e){

            }
        }
    }




}
