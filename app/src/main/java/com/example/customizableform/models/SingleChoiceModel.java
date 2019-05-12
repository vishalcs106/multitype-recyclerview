package com.example.customizableform.models;

public class SingleChoiceModel extends BaseFormItemModel{
    private String[] options;
    private String selection;

    public SingleChoiceModel(String id, String itemType, String title, String[] options) {
        super(id, itemType, title);
        this.options = options;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }
}
