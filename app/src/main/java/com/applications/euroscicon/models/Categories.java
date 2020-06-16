package com.applications.euroscicon.models;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Categories implements Serializable {

    private boolean isChecked = false;
    private String regproducts_id;
    private String title;
    private String price1;
    private String price2;
    private String price3;
    private String currencyType;
    private String type;
    private String earlyDate;
    private String normalDate;
    private String finalDate;
    private int quantity;
    private String status;


    public Categories() {
    }

    public Categories(String regproducts_id, String title, String price1, String price2, String price3, String currencyType, String type, String earlyDate, String normalDate, String finalDate, int quantity,String status) {

        this.regproducts_id = regproducts_id;
        this.title = title;
        this.price1 = price1;
        this.price2 = price2;
        this.price3 = price3;
        this.currencyType = currencyType;
        this.type = type;
        this.earlyDate = earlyDate;
        this.normalDate = normalDate;
        this.finalDate = finalDate;
        this.quantity = quantity;
        this.status = status;
    }

    public Categories(String regproducts_id,String title, String price1, String price2, String price3, String currencyType, String type, String earlyDate, String normalDate, String finalDate,String status) {

        this.regproducts_id = regproducts_id;
        this.title = title;
        this.price1 = price1;
        this.price2 = price2;
        this.price3 = price3;
        this.currencyType = currencyType;
        this.type = type;
        this.earlyDate = earlyDate;
        this.normalDate = normalDate;
        this.finalDate = finalDate;
        this.status = status;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    public String getPrice3() {
        return price3;
    }

    public void setPrice3(String price3) {
        this.price3 = price3;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEarlyDate() {
        return earlyDate;
    }

    public void setEarlyDate(String earlyDate) {
        this.earlyDate = earlyDate;
    }

    public String getNormalDate() {
        return normalDate;
    }

    public void setNormalDate(String normalDate) {
        this.normalDate = normalDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @NotNull
    @Override
    public String toString() {
        return title;
    }
}
