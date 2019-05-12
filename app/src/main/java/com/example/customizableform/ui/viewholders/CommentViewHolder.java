package com.example.customizableform.ui.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.customizableform.R;
import com.example.customizableform.models.CommentModel;
import com.example.customizableform.ui.customviews.CommentView;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    private CommentView commentView;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);
        commentView = itemView.findViewById(R.id.commentView);
    }

    public void bind(CommentModel commentModel){

    }

}
