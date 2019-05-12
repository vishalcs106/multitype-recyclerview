package com.example.customizableform.ui.customviews;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.customizableform.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class SingleChoiceView extends RelativeLayout {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.choices_rg)
    RadioGroup radioGroup;

    private String selection = "";

    public SingleChoiceView(Context context) {
        super(context);
        init();
    }

    public SingleChoiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SingleChoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @OnCheckedChanged(R.id.choices_rg)
    public void choiceSelected(CompoundButton radioButton, boolean selected){
        if(selected){
            selection = radioButton.getText().toString();
        }
    }

    private void init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.customview_singlechoice, this, true);
        ButterKnife.bind(this, view);
    }

    public void setTitle(String title){
        if(!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
        }
    }

    public void setChoices(String[] choices){
        for(int i=0; i<choices.length; i++){
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(choices[i]);
            radioGroup.addView(radioButton);
        }
    }

    public String getSelection() {
        return selection;
    }
}
