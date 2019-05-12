package com.example.customizableform.ui.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.example.customizableform.R;
import com.example.customizableform.models.PhotoModel;
import com.example.customizableform.ui.customviews.PhotoView;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    private PhotoView photoView;

    public PhotoViewHolder(@NonNull View itemView) {
        super(itemView);
        photoView = itemView.findViewById(R.id.photoView);
    }

    public void bind(PhotoModel photoModel){
        if(!TextUtils.isEmpty(photoModel.getUrl())){
            photoView.setImage(photoModel.getUrl());
        }
    }

}
