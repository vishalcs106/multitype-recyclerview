package com.example.customizableform.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.customizableform.R;
import com.example.customizableform.interfaces.ImageUpLoadListener;
import com.example.customizableform.interfaces.PhotoViewListener;
import com.example.customizableform.models.BaseFormItemModel;
import com.example.customizableform.models.CommentModel;
import com.example.customizableform.models.PhotoModel;
import com.example.customizableform.models.SingleChoiceModel;
import com.example.customizableform.utils.AssetJsonReader;
import com.example.customizableform.utils.GalleryToCloudinaryHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private int photoClickPosition = 0;
    private FormAdapter adapter;
    private List<BaseFormItemModel> formItemModels = new ArrayList<>();

    private PhotoViewListener photoViewListener = new PhotoViewListener() {
        @Override
        public void openGallery(int position) {
            photoClickPosition = position;
            if(checkPermissionForReadExtertalStorage()) {
                openImagePicker();
            } else {
                requestPermissionForReadExtertalStorage();
            }
        }

        @Override
        public void clearPhoto(int position) {
            adapter.updateImageAt(position, "");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);
        formItemModels = new AssetJsonReader().readJsonAndReturnList(this);
       initRecyclerview();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_submit:
                prepareJson();
                break;
            default:
                break;
        }
        return true;
    }

    private void initRecyclerview() {
        adapter = new FormAdapter(formItemModels);
        adapter.setPhotoViewListener(photoViewListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void openImagePicker(){
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, 22);
    }

    private void prepareJson(){
        JSONArray jsonObject = new JSONArray();
        formItemModels = adapter.getFormList();
        for(BaseFormItemModel baseFormItemModel : formItemModels){
            if(baseFormItemModel instanceof PhotoModel){
                PhotoModel photoModel = (PhotoModel) baseFormItemModel;
                jsonObject.put(photoModel.getJson());
            }
             else if(baseFormItemModel instanceof CommentModel){
                CommentModel commentModel = (CommentModel) baseFormItemModel;
                jsonObject.put(commentModel.getJson());
            }
             else if(baseFormItemModel instanceof SingleChoiceModel){
                SingleChoiceModel singleChoiceModel = (SingleChoiceModel) baseFormItemModel;
                jsonObject.put(singleChoiceModel.getJson());
            }
        }
        Dialog dialog = new Dialog(this, R.style.WideDialog);
        dialog.setContentView(R.layout.pretty_json_dialog);
        TextView textView = dialog.findViewById(R.id.json);
        try {
            textView.setText(jsonObject.toString(2));
            textView.setTextColor(getResources().getColor(android.R.color.black));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.setCancelable(true);
        dialog.show();
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
