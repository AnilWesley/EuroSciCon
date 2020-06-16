package com.applications.euroscicon.models;

public class CheckBoxItem {
    public boolean checked;
    String id;
    String ItemString;
    /*CheckBoxItem(Drawable drawable, String t, boolean b){
        ItemDrawable = drawable;
        ItemString = t;
        checked = b;
    }

    public boolean isChecked(){
        return checked;
    }*/

    public CheckBoxItem(boolean checked, String id, String itemString) {
        this.checked = checked;
        this.id = id;
        ItemString = itemString;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemString() {
        return ItemString;
    }

    public void setItemString(String itemString) {
        ItemString = itemString;
    }

}
