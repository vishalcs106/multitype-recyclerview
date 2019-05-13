package com.example.customizableform.ui.customviews;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.customizableform.R;
import com.example.customizableform.interfaces.ChoiceSelectionListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class SingleChoiceView extends RelativeLayout {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.choices_rg)
    RadioGroup radioGroup;
    private String[] choices;
    private String selection = "";
    private ChoiceSelectionListener choiceSelectionListener;
    private int position;
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

    private void init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.customview_singlechoice, this, true);
        ButterKnife.bind(this, view);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                if(rb.isChecked()){
                    selection = rb.getText().toString();
                } else {
                    selection = "";
                }
                choiceSelectionListener.onSelectionChange(position, selection);
            }
        });

    }

    public void setTitle(String title){
        if(!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
        }
    }

    public void setChoices(String[] choices){
        if(this.choices == null) {
            this.choices = choices;
            for (int i = 0; i < choices.length; i++) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(choices[i]);
                radioGroup.addView(radioButton);
            }
        }
    }

    public String getSelection() {
        return selection;
    }

    public void setChoiceSelectionListener(ChoiceSelectionListener choiceSelectionListener) {
        this.choiceSelectionListener = choiceSelectionListener;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
