package com.applications.euroscicon.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AddOnItem implements Parcelable {

    private String regproducts_id;
    private String title;
    private String price;
    private String status;
    private String subTitle;

    public AddOnItem() {
    }

    public AddOnItem(String regproducts_id, String title, String price, String status, String subTitle) {
        this.regproducts_id = regproducts_id;
        this.title = title;
        this.price = price;
        this.status = status;
        this.subTitle = subTitle;
    }

    private AddOnItem(Parcel in) {
        regproducts_id = in.readString();
        title = in.readString();
        price = in.readString();
        status = in.readString();
        subTitle = in.readString();
    }

    public static final Creator<AddOnItem> CREATOR = new Creator<AddOnItem>() {
        @Override
        public AddOnItem createFromParcel(Parcel in) {
            return new AddOnItem(in);
        }

        @Override
        public AddOnItem[] newArray(int size) {
            return new AddOnItem[size];
        }
    };

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getRegproducts_id() {
        return regproducts_id;
    }

    public void setRegproducts_id(String regproducts_id) {
        this.regproducts_id = regproducts_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(regproducts_id);
        dest.writeString(title);
        dest.writeString(price);
        dest.writeString(status);
        dest.writeString(subTitle);
    }
}
