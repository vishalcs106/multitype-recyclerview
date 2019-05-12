package com.example.customizableform.ui.customviews;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.customizableform.R;
import com.example.customizableform.interfaces.OpenGalleryListener;

import butterknife.BindView;
import butterknife.OnClick;

public class PhotoView extends RelativeLayout {
    @BindView(R.id.photo_iv)
    ImageView photoIv;
    @BindView(R.id.clear_iv)
    ImageView clearIcon;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private OpenGalleryListener openGalleryListener;

    private String imageUrl = "";
    public PhotoView(Context context) {
        super(context);
        init();
    }

    public PhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOpenGalleryListener(OpenGalleryListener openGalleryListener) {
        this.openGalleryListener = openGalleryListener;
    }

    @OnClick({R.id.clear_iv, R.id.photo_iv})
    public void onCLick(View view){
        switch (view.getId()){
            case R.id.clear_iv:
                clearPhoto();
                break;
            case R.id.photo_iv:
                photoClicked();
                break;
        }
    }

    private void clearPhoto(){
        imageUrl = "";
        clearIcon.setVisibility(GONE);
    }

    private void photoClicked(){
        if(TextUtils.isEmpty(imageUrl)){
            openImagePicker();
        } else {
            enlargeImage();
        }
    }

    private void enlargeImage() {
        Context context = getContext();
        if(context != null && !((Activity)context).isFinishing()) {
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.view_photo_enlarge);
            dialog.findViewById(R.id.close_iv).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

    }

    private void openImagePicker() {
       openGalleryListener.openGallery();
       progressBar.setVisibility(VISIBLE);
    }


    private void init() {

    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImage(String url) {

    }
}
