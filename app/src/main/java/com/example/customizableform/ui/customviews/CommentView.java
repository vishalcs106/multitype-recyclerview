package com.example.customizableform.ui.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.customizableform.R;
import com.example.customizableform.interfaces.CommentViewListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnTextChanged;

public class CommentView extends RelativeLayout {

    @BindView(R.id.comment_et)
    EditText commentEt;

    private String comment = "";
    private boolean isCommentOn = false;
    private int position;
    private CommentViewListener commentViewListener;

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

    public void setCommentViewListener(CommentViewListener commentViewListener) {
        this.commentViewListener = commentViewListener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @OnCheckedChanged(R.id.comment_switch)
    public void switchToggeled(CompoundButton commentSwitch, boolean isCommentOn){
        this.isCommentOn = isCommentOn;
        commentViewListener.onCommentSwitchChange(position, isCommentOn);
        if(isCommentOn){
            commentEt.setVisibility(VISIBLE);
        } else {
            commentEt.setVisibility(GONE);
        }
    }

    @OnTextChanged(value = R.id.comment_et, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void commentTextChange(CharSequence text) {
        comment = text.toString();
        commentViewListener.onCommentTextChanged(position, comment);
    }

    private void init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.customview_comment, this, true);
        ButterKnife.bind(this, view);
    }

    public String getComment() {
        return comment;
    }
}
