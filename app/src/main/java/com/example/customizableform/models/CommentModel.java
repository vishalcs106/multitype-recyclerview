package com.example.customizableform.models;

public class CommentModel extends BaseFormItemModel{
    private boolean isCommentOn;
    private String comment;

    public CommentModel(String id, String itemType, String title) {
        super(id, itemType, title);
    }

    public boolean isCommentOn() {
        return isCommentOn;
    }

    public void setCommentOn(boolean commentOn) {
        isCommentOn = commentOn;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
