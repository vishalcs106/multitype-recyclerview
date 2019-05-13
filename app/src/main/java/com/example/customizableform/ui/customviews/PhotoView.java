package com.example.customizableform.ui.customviews;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.customizableform.R;
import com.example.customizableform.interfaces.PhotoViewListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoView extends RelativeLayout {
    @BindView(R.id.photo_iv)
    ImageView photoIv;
    @BindView(R.id.clear_iv)
    ImageView clearIcon;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.title_tv)
    TextView titleTv;

    private PhotoViewListener photoViewListener;
    private int position;
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

    public void setPosition(int position) {
        this.position = position;
    }

    public void setPhotoViewListener(PhotoViewListener photoViewListener) {
        this.photoViewListener = photoViewListener;
    }

    @OnClick({R.id.clear_iv, R.id.photo_iv})
    public void onCLick(View view){
        switch (view.getId()){
            case R.id.clear_iv:
                clearImage();
                break;
            case R.id.photo_iv:
                photoClicked();
                break;
        }
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
            final Dialog dialog = new Dialog(getContext(), R.style.WideDialog);
            dialog.setContentView(R.layout.view_photo_enlarge);
            String imgurl = "https://res.cloudinary.com/db7iorevu/image/upload/"+"w_600,h_1000/"+imageUrl;
            Glide.with(context).load(imgurl).centerCrop().into((ImageView) dialog.findViewById(R.id.enlarged_photo_iv));
            dialog.findViewById(R.id.close_iv).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

    }

    private void openImagePicker() {
       photoViewListener.openGallery(position);
       progressBar.setVisibility(VISIBLE);
    }


    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.customview_photo, this, true);
        ButterKnife.bind(this, view);
        if(TextUtils.isEmpty(imageUrl))
            clearImage();
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImage(String url) {
        imageUrl = url;
        progressBar.setVisibility(GONE);
        if(!TextUtils.isEmpty(url)) {
            clearIcon.setVisibility(VISIBLE);
            String imgurl = "https://res.cloudinary.com/db7iorevu/image/upload/"+"w_200,h_200/"+url;
            Glide.with(getContext()).load(imgurl).centerCrop().into(photoIv);
        } else{
            clearImage();
        }
    }

    public void clearImage(){
        photoIv.setImageResource(0);
        clearIcon.setVisibility(GONE);
        progressBar.setVisibility(GONE);
        if(photoViewListener != null && !TextUtils.isEmpty(imageUrl)) {
            photoViewListener.clearPhoto(position);
        }
        imageUrl = "";
    }

    public void setTitle(String title) {
        titleTv.setText(title);
    }
}
