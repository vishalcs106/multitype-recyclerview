package com.example.customizableform.ui.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.customizableform.R;
import com.example.customizableform.models.SingleChoiceModel;
import com.example.customizableform.ui.customviews.SingleChoiceView;

public class SingleChoiceViewHolder extends RecyclerView.ViewHolder {

    private SingleChoiceView singleChoiceView;

    public SingleChoiceViewHolder(@NonNull View itemView) {
        super(itemView);
        singleChoiceView = itemView.findViewById(R.id.singlechoiceView);
    }

    public void bind(SingleChoiceModel singleChoiceModel){
        singleChoiceView.setTitle(singleChoiceModel.getTitle());
        singleChoiceView.setChoices(singleChoiceModel.getOptions());
    }
}
