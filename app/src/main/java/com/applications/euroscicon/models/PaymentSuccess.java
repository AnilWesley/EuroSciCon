package com.applications.euroscicon.models;

import java.util.List;

public class PaymentSuccess {


    /**
     * amount : 79900
     * canceledAt : 0
     * captureMethod : automatic
     * clientSecret : pi_1GNuXfGn5AtBIcmKHUeVJ6fR_secret_Fje3NuysDlHJkeaum0044RzPL
     * confirmationMethod : automatic
     * created : 1584509651
     * currency : gbp
     * description : Software development services
     * id : pi_1GNuXfGn5AtBIcmKHUeVJ6fR
     * isLiveMode : false
     * objectType : payment_intent
     * paymentMethodTypes : ["card"]
     * status : Succeeded
     */

    private int amount;
    private int canceledAt;
    private String captureMethod;
    private String clientSecret;
    private String confirmationMethod;
    private int created;
    private String currency;
    private String description;
    private String id;
    private boolean isLiveMode;
    private String objectType;
    private String status;
    private List<String> paymentMethodTypes;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(int canceledAt) {
        this.canceledAt = canceledAt;
    }

    public String getCaptureMethod() {
        return captureMethod;
    }

    public void setCaptureMethod(String captureMethod) {
        this.captureMethod = captureMethod;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getConfirmationMethod() {
        return confirmationMethod;
    }

    public void setConfirmationMethod(String confirmationMethod) {
        this.confirmationMethod = confirmationMethod;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsLiveMode() {
        return isLiveMode;
    }

    public void setIsLiveMode(boolean isLiveMode) {
        this.isLiveMode = isLiveMode;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getPaymentMethodTypes() {
        return paymentMethodTypes;
    }

    public void setPaymentMethodTypes(List<String> paymentMethodTypes) {
        this.paymentMethodTypes = paymentMethodTypes;
    }
}
