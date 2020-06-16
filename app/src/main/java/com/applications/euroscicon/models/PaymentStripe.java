package com.applications.euroscicon.models;

import java.io.Serializable;

public class PaymentStripe implements Serializable {


    /*
     * publishableKey : pk_test_8vi7MKn3JDDLkpGqMjCFLmMV00eNpezEus
     * clientSecret : pi_1GNZ5dGn5AtBIcmKaIi1WFA4_secret_5rVcJMnxUIMYtaZ6Sc9UkpY2J
     */

    private String publishableKey;
    private String clientSecret;

    public String getPublishableKey() {
        return publishableKey;
    }

    public void setPublishableKey(String publishableKey) {
        this.publishableKey = publishableKey;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }


}
