package com.example.customizableform.ui.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.customizableform.R;
import com.example.customizableform.interfaces.ChoiceSelectionListener;
import com.example.customizableform.models.SingleChoiceModel;
import com.example.customizableform.ui.customviews.SingleChoiceView;

public class SingleChoiceViewHolder extends RecyclerView.ViewHolder {

    private SingleChoiceView singleChoiceView;
    private ChoiceSelectionListener choiceSelectionListener;

    public SingleChoiceViewHolder(@NonNull View itemView) {
        super(itemView);
        singleChoiceView = itemView.findViewById(R.id.singlechoiceView);
    }

    public void bind(SingleChoiceModel singleChoiceModel){
        singleChoiceView.setPosition(getAdapterPosition());
        singleChoiceView.setChoiceSelectionListener(choiceSelectionListener);
        singleChoiceView.setTitle(singleChoiceModel.getTitle());
        singleChoiceView.setChoices(singleChoiceModel.getOptions(), singleChoiceModel.getSelection());
    }

    public void setChoiceSelectionListener(ChoiceSelectionListener choiceSelectionListener) {
        this.choiceSelectionListener = choiceSelectionListener;
    }
}
