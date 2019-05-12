package com.example.customizableform.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.customizableform.interfaces.OpenGalleryListener;
import com.example.customizableform.models.BaseFormItemModel;
import com.example.customizableform.models.CommentModel;
import com.example.customizableform.models.PhotoModel;
import com.example.customizableform.models.SingleChoiceModel;
import com.example.customizableform.ui.viewholders.CommentViewHolder;
import com.example.customizableform.ui.viewholders.PhotoViewHolder;
import com.example.customizableform.ui.viewholders.SingleChoiceViewHolder;
import com.example.customizableform.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private List<BaseFormItemModel> formItemModels = new ArrayList<>();
    private OpenGalleryListener openGalleryListener;

    public FormAdapter(List<BaseFormItemModel> formItemModels) {
        this.formItemModels = formItemModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        BaseFormItemModel baseFormItemModel = formItemModels.get(position);
        switch (baseFormItemModel.getItemType()){
            case Constants.TYPE_COMMENT : return new CommentViewHolder(viewGroup);
            case Constants.TYPE_PHOTO : return new PhotoViewHolder(viewGroup);
            case Constants.TYPE_SINGLE_CHOICE :return new SingleChoiceViewHolder(viewGroup);
        }
        return  null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        BaseFormItemModel baseFormItemModel = formItemModels.get(position);
        switch (baseFormItemModel.getItemType()) {
            case Constants.TYPE_COMMENT:
                ((CommentViewHolder) viewHolder).bind((CommentModel) baseFormItemModel);
            case Constants.TYPE_PHOTO:
                if(baseFormItemModel instanceof PhotoModel) {
                    ((PhotoViewHolder) viewHolder).bind((PhotoModel) baseFormItemModel);
                }
            case Constants.TYPE_SINGLE_CHOICE:
                if(baseFormItemModel instanceof SingleChoiceModel) {
                    ((SingleChoiceViewHolder) viewHolder).bind((SingleChoiceModel) baseFormItemModel);
                }
        }
    }

    @Override
    public int getItemViewType(int position) {
        BaseFormItemModel baseFormItemModel = formItemModels.get(position);
        switch (baseFormItemModel.getItemType()){
            case Constants.TYPE_COMMENT : return Constants.ITEM_TYPE_COMMENT;
            case Constants.TYPE_PHOTO : return Constants.ITEM_TYPE_PHOTO;
            case Constants.TYPE_SINGLE_CHOICE : return Constants.ITEM_TYPE_SINGLE_CHOICE;
            default:return -1;
        }
    }

    @Override
    public int getItemCount() {
        return formItemModels.size();
    }

    public void setOpenGalleryListener(OpenGalleryListener openGalleryListener) {
        this.openGalleryListener = openGalleryListener;
    }

    public void updateImageAt(int photoClickPosition, String imageUrl) {
        PhotoModel photoModel = (PhotoModel) formItemModels.get(photoClickPosition);
        photoModel.setUrl(imageUrl);
        formItemModels.set(photoClickPosition, photoModel);
        notifyItemChanged(photoClickPosition);
    }
}
