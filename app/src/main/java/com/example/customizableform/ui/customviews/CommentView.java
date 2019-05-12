package com.example.customizableform.ui.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.customizableform.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnTextChanged;

public class CommentView extends RelativeLayout {

    @BindView(R.id.comment_et)
    EditText commentEt;

    private String comment = "";
    private boolean isCommentOn = false;

    public CommentView(Context context) {
        super(context);
        init();
    }

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @OnCheckedChanged(R.id.comment_switch)
    public void switchToggeled(CompoundButton commentSwitch, boolean isCommentOn){
        this.isCommentOn = isCommentOn;
        if(isCommentOn){
            commentEt.setVisibility(VISIBLE);
        } else {
            commentEt.setVisibility(GONE);
        }
    }

    @OnTextChanged(value = R.id.comment_et, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void commentTextChange(CharSequence text) {
        comment = text.toString();
    }

    private void init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.customview_comment, this, true);
        ButterKnife.bind(this, view);
    }

    public String getComment() {
        return comment;
    }
}
