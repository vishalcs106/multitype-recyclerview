package com.example.customizableform.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.customizableform.R;
import com.example.customizableform.interfaces.ChoiceSelectionListener;
import com.example.customizableform.interfaces.CommentViewListener;
import com.example.customizableform.interfaces.PhotoViewListener;
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
    private PhotoViewListener photoViewListener;

    public FormAdapter(List<BaseFormItemModel> formItemModels) {
        this.formItemModels = formItemModels;
        setHasStableIds(true);
    }

    private CommentViewListener commentViewListener = new CommentViewListener() {
        @Override
        public void onCommentTextChanged(int position, String comment) {
            CommentModel commentModel = (CommentModel) formItemModels.get(position);
            commentModel.setComment(comment);
            formItemModels.set(position, commentModel);
        }

        @Override
        public void onCommentSwitchChange(int position, boolean switchOn) {
            CommentModel commentModel = (CommentModel) formItemModels.get(position);
            commentModel.setCommentOn(switchOn);
            formItemModels.set(position, commentModel);
        }
    };

    private ChoiceSelectionListener choiceSelectionListener = new ChoiceSelectionListener() {
        @Override
        public void onSelectionChange(int position, String selection) {
            SingleChoiceModel singleChoiceModel = (SingleChoiceModel) formItemModels.get(position);
            singleChoiceModel.setSelection(selection);
            formItemModels.set(position, singleChoiceModel);
        }
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        BaseFormItemModel baseFormItemModel = formItemModels.get(position);
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view;
        switch (baseFormItemModel.getItemType()){
            case Constants.TYPE_COMMENT :
                view = layoutInflater.inflate(R.layout.list_item_comment, viewGroup, false);
                CommentViewHolder commentViewHolder = new CommentViewHolder(view);
                commentViewHolder.setCommentViewListener(commentViewListener);
                return commentViewHolder;
            case Constants.TYPE_PHOTO : view = layoutInflater.inflate(R.layout.list_item_photo, viewGroup, false);
                PhotoViewHolder viewHolder = new PhotoViewHolder(view);
                viewHolder.setPhotoViewListener(photoViewListener);
                return viewHolder;
            case Constants.TYPE_SINGLE_CHOICE : view = layoutInflater.inflate(R.layout.list_item_single_choice, viewGroup, false);
                SingleChoiceViewHolder singleChoiceViewHolder = new SingleChoiceViewHolder(view);
                singleChoiceViewHolder.setChoiceSelectionListener(choiceSelectionListener);
                return singleChoiceViewHolder;
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
    public long getItemId(int position) {
        return position;
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

    public void setPhotoViewListener(PhotoViewListener photoViewListener) {
        this.photoViewListener = photoViewListener;
    }

    public void updateImageAt(int photoClickPosition, String imageUrl) {
        PhotoModel photoModel = (PhotoModel) formItemModels.get(photoClickPosition);
        photoModel.setUrl(imageUrl);
        formItemModels.set(photoClickPosition, photoModel);
        notifyItemChanged(photoClickPosition);
    }

    public List<BaseFormItemModel> getFormList(){
        return formItemModels;
    }

}
