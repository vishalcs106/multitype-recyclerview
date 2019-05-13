package com.example.customizableform.interfaces;

public interface CommentViewListener {
    void onCommentTextChanged(int position, String comment);
    void onCommentSwitchChange(int position, boolean switchOn);
}
