package com.applications.euroscicon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.euroscicon.R;
import com.applications.euroscicon.adapters.CheckoutAdapter;
import com.applications.euroscicon.api.ApiInterface;
import com.applications.euroscicon.api.RetrofitClient;
import com.applications.euroscicon.models.AddOnItem;
import com.applications.euroscicon.models.Categories;
import com.applications.euroscicon.models.PaymentRegistration;
import com.applications.euroscicon.models.PaymentStripe;
import com.applications.euroscicon.models.PaymentSuccess;
import com.applications.euroscicon.utils.MyAppPrefsManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CheckOutActivity extends AppCompatActivity {
    private static final String TAG = "RESPONSE_DATA";

    Categories categories;
    ArrayList<AddOnItem> addOnItemArrayList = new ArrayList<>();
    ArrayList<AddOnItem> addOnItemArrayList1 = new ArrayList<>();
    ArrayList<AddOnItem> addOnItemArrayList2 = new ArrayList<>();
    ArrayList<AddOnItem> addOnItemArrayList3 = new ArrayList<>();
    ArrayList<AddOnItem> addOnItemArrayList4 = new ArrayList<>();
    String catProductTitle, catProduct, catProduct_Id, yrfProduct, yrfProduct_Id, ePosterProduct, ePosterProduct_Id, currencyType, catPrice, yrfPrice, eposterPrice;
    @BindView(R.id.checkoutRecycler)
    RecyclerView checkoutRecycler;

    String conf_Id, userName, emailID, mobileNumber, country, productType;

    private static final String BACKEND_URL = "https://www.conferenceseries.com/stripe/";
    @BindView(R.id.cardInputWidget)
    CardInputWidget cardInputWidget;
    @BindView(R.id.payButton)
    Button payButton;

    String paymentIntentClientSecret;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtPrice)
    TextView txtPrice;
    @BindView(R.id.progressBar)
    LinearLayout progressBar;
    private Stripe stripe;
    String totalAmount;
    ApiInterface apiInterface;

    int amount;

    String reg_id, actionTitle,shorttitle,conf_type;

    List<PaymentStripe> paymentStripeList;

    MyAppPrefsManager myAppPrefsManager;
    String app_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Check Out");
        apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);
        addOnItemArrayList.clear();
        myAppPrefsManager = new MyAppPrefsManager(CheckOutActivity.this);
        app_user_id=myAppPrefsManager.getUserId();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("checkOutData");
        if (bundle != null) {
            //addOnItemArrayList = (ArrayList<AddOnItem>) bundle.getSerializable("addonList");
            addOnItemArrayList = bundle.getParcelableArrayList("addonList");
            conf_Id = bundle.getString("conf_Id");
            actionTitle = bundle.getString("title");
            shorttitle = bundle.getString("shorttitle");
            conf_type = bundle.getString("conf_type");
            userName = bundle.getString("userName");
            mobileNumber = bundle.getString("mobileNumber");
            emailID = bundle.getString("emailID");
            country = bundle.getString("country");
            productType = bundle.getString("productType");

            catProductTitle = bundle.getString("catProductTitle");
            catProduct = bundle.getString("catProduct");
            catProduct_Id = bundle.getString("catProduct_Id");
            catPrice = bundle.getString("catPrice");
            yrfProduct = bundle.getString("yrfProduct");
            yrfProduct_Id = bundle.getString("yrfProduct_Id");
            yrfPrice = bundle.getString("yrfPrice");
            ePosterProduct = bundle.getString("ePosterProduct");
            ePosterProduct_Id = bundle.getString("ePosterProduct_Id");
            eposterPrice = bundle.getString("eposterPrice");
            currencyType = bundle.getString("currencyType");
            totalAmount = bundle.getString("totalAmount");

            assert totalAmount != null;
            amount = Integer.parseInt(totalAmount) * 100;
            Log.d(TAG, "onCreate: " + amount);

            // catProduct data
            if (!catProduct.equalsIgnoreCase("") || !catProduct_Id.equalsIgnoreCase("") || !catPrice.equalsIgnoreCase("0")) {
                addOnItemArrayList1.add(new AddOnItem(catProduct_Id, catProduct, catPrice, "add", catProductTitle));
            } else {
                addOnItemArrayList1.clear();
            }

            addOnItemArrayList.addAll(addOnItemArrayList1);

            // yrfProduct data
            if (!yrfProduct.equalsIgnoreCase("") || !yrfProduct_Id.equalsIgnoreCase("") || !yrfPrice.equalsIgnoreCase("0")) {
                addOnItemArrayList2.add(new AddOnItem(yrfProduct_Id, yrfProduct, yrfPrice, "add", "Young Research Form"));
            } else {
                addOnItemArrayList2.clear();
            }
            addOnItemArrayList.addAll(addOnItemArrayList2);

            // eposterProduct data
            if (!ePosterProduct.equalsIgnoreCase("") || !ePosterProduct_Id.equalsIgnoreCase("") || !eposterPrice.equalsIgnoreCase("0")) {
                addOnItemArrayList3.add(new AddOnItem(ePosterProduct_Id, ePosterProduct, eposterPrice, "add", "Eposter"));
            } else {
                addOnItemArrayList3.clear();

            }
            addOnItemArrayList.addAll(addOnItemArrayList3);

            jsonData(addOnItemArrayList);

        }

        checkoutRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        checkoutRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        CheckoutAdapter checkoutAdapter = new CheckoutAdapter(this, addOnItemArrayList, null, currencyType);

        checkoutRecycler.setAdapter(checkoutAdapter);
        checkoutAdapter.notifyDataSetChanged();

        payButton.setText("Pay : " + currencyType + totalAmount);


        startCheckOut1();


    }


    public void jsonData(ArrayList<AddOnItem> addOnItemArrayList) {


        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("conf_id", conf_Id);
            jsonObject.addProperty("conf_title", actionTitle);
            jsonObject.addProperty("name", userName);
            jsonObject.addProperty("email", emailID);
            jsonObject.addProperty("phone", mobileNumber);
            jsonObject.addProperty("country", country);
            jsonObject.addProperty("amount", totalAmount);
            jsonObject.addProperty("gateway", "stripe");
            jsonObject.addProperty("payment_page", "android");
            jsonObject.addProperty("source", "android");
            jsonObject.addProperty("app_user_id", app_user_id);

            if (currencyType.equalsIgnoreCase("$")) {
                jsonObject.addProperty("currency", "usd");
            } else if (currencyType.equalsIgnoreCase("\u20ac")) {
                jsonObject.addProperty("currency", "euro");
            } else {
                jsonObject.addProperty("currency", "gbp");
            }
            JsonArray jsonArray = new JsonArray();

            for (AddOnItem addOnItem : addOnItemArrayList) {
                JsonObject imgObject = new JsonObject();
                imgObject.addProperty("product_id", addOnItem.getRegproducts_id());
                imgObject.addProperty("product_type", productType);
                jsonArray.add(imgObject);

            }
            jsonObject.add("products", jsonArray);


            Log.d(TAG, "calculateCartTotal: " + jsonObject);


            apiInterface.processDataSendConferencePayment(jsonObject).enqueue(new Callback<PaymentRegistration>() {
                @Override
                public void onResponse(@NotNull Call<PaymentRegistration> call, @NotNull Response<PaymentRegistration> response) {

                    if (response.isSuccessful()) {
                        assert response.body() != null;

                        List<PaymentRegistration.RegistrationDetailsBean> payment1s = response.body().getRegistration_details();
                        Log.d(TAG, "onResponse: " + payment1s.get(0).getAmount());
                        reg_id = payment1s.get(0).getReg_id();


                    } else {
                        Log.d(TAG, "onFailure:" + response.toString());
                    }


                }

                @Override
                public void onFailure(@NotNull Call<PaymentRegistration> call, @NotNull Throwable t) {

                    Log.d(TAG, "onFailure:" + t.toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void startCheckOut1() {

        JsonObject jsonObject = new JsonObject();
        if (currencyType.equalsIgnoreCase("$")) {
            jsonObject.addProperty("currency", "USD");
        } else if (currencyType.equalsIgnoreCase("\u20ac")) {
            jsonObject.addProperty("currency", "EURO");

        } else {
            jsonObject.addProperty("currency", "GBP");
        }
        jsonObject.addProperty("amount", amount);
        jsonObject.addProperty("description", actionTitle);
        jsonObject.addProperty("receipt_email", emailID);
        jsonObject.addProperty("source", "android");

        Log.d(TAG, "RESPONSE_ID" + jsonObject);
        apiInterface.processDataConferencePayment(jsonObject).enqueue(new PayCallback(CheckOutActivity.this));

        payButton.setOnClickListener((View view) -> {

            progressBar.setVisibility(View.VISIBLE);
            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
            if (params != null) {
                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                        .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                stripe.confirmPayment(this, confirmParams);
            } else {
                Log.d(TAG, "startCheckout: Payment");
            }
        });
    }


    private void displayAlert(@NonNull String alertTitle, @Nullable String message) {


        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.success_payment, viewGroup, false);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        TextView txtTitle = dialogView.findViewById(R.id.txtTitle);
        TextView txtMsg = dialogView.findViewById(R.id.txtMsg);
        ImageView image = dialogView.findViewById(R.id.image);
        Button buttonOk = dialogView.findViewById(R.id.buttonOk);


        if (alertTitle.equalsIgnoreCase("Payment success")) {

            Gson gson = new Gson();
            PaymentSuccess paymentSuccess = gson.fromJson(message, PaymentSuccess.class);
            Log.d(TAG, "displayAlert: " + paymentSuccess.getId());
            paymentData(paymentSuccess.getId(), paymentSuccess.getStatus());

            txtTitle.setText(alertTitle);
            txtMsg.setText("Thanks for your Registration. Your payment is successfull. We will notify you shortly.");
            image.setImageResource(R.drawable.ic_success);
            buttonOk.setOnClickListener(v -> {


              /*  myAppPrefsManager.setUserLoggedIn(true);
                myAppPrefsManager.setUserConfId(conf_Id);
                myAppPrefsManager.setUserConfTitle(title);
                // Set isLogged_in of ConstantValues
                ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();*/
                Intent intent = new Intent(CheckOutActivity.this, DashBoardActivity.class);
                intent.putExtra("id",conf_Id);
                intent.putExtra("title",actionTitle);
                intent.putExtra("shorttitle",shorttitle);
                intent.putExtra("conf_type",conf_type);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                alertDialog.dismiss();


            });

        } else if (alertTitle.equalsIgnoreCase("Payment failed")) {

            paymentData("", "Failure");
            txtTitle.setText(alertTitle);
            txtMsg.setText("Insufficient funds. Please use another Card for Payment.");
            image.setImageResource(R.drawable.ic_close_white_24dp);
            buttonOk.setOnClickListener(v -> {
                CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
                cardInputWidget.clear();
                alertDialog.dismiss();

            });
        } else {
            paymentData("", "Failure");
            txtTitle.setText(alertTitle);
            txtMsg.setText("Insufficient funds. Please use another Card for Payment.");
            image.setImageResource(R.drawable.ic_close_white_24dp);
            buttonOk.setOnClickListener(v -> {
                CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
                cardInputWidget.clear();
                alertDialog.dismiss();

            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }


    private void onPaymentSuccess(@NonNull final Response<PaymentStripe> response) {

        if (response.isSuccessful()) {

            assert response.body() != null;
            Log.d(TAG, "onResponse: " + response.body().getPublishableKey());
            // The response from the server includes the Stripe publishable key and
            // PaymentIntent details.
            // For added security, our sample app gets the publishable key from the server
            String stripePublishableKey = response.body().getPublishableKey();
            paymentIntentClientSecret = response.body().getClientSecret();

            // Configure the SDK with your Stripe publishable key so that it can make requests to the Stripe API
            stripe = new Stripe(
                    getApplicationContext(),
                    Objects.requireNonNull(stripePublishableKey)
            );

        }


    }


    private static final class PayCallback implements Callback<PaymentStripe> {


        @NonNull
        private final WeakReference<CheckOutActivity> activityRef;

        PayCallback(@NonNull CheckOutActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onResponse(@NotNull Call<PaymentStripe> call, @NotNull Response<PaymentStripe> response) {
            final CheckOutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            if (!response.isSuccessful()) {

                activity.runOnUiThread(() ->
                        Toast.makeText(
                                activity, "Error: " + response.toString(), Toast.LENGTH_LONG
                        ).show()
                );
                Log.d(TAG, "onFailure: " + response.toString());

            } else {

                activity.onPaymentSuccess(response);
            }
        }

        @Override
        public void onFailure(@NotNull Call<PaymentStripe> call, @NotNull Throwable t) {
            final CheckOutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            activity.runOnUiThread(() ->

                    Toast.makeText(
                            activity, "Error: " + t.toString(), Toast.LENGTH_LONG
                    ).show()

            );

            Log.d(TAG, "onFailure: " + t.toString());
        }
    }

    private final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull
        private final WeakReference<CheckOutActivity> activityRef;

        PaymentResultCallback(@NonNull CheckOutActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onError(@NotNull Exception e) {
            final CheckOutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString());
            progressBar.setVisibility(View.GONE);
            Log.d(TAG, "onFailure: " + e.toString());
        }

        @Override
        public void onSuccess(PaymentIntentResult paymentIntentResult) {
            final CheckOutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = paymentIntentResult.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                activity.displayAlert("Payment success", gson.toJson(paymentIntent));
                Log.d(TAG, "onSuccess: " + gson.toJson(paymentIntent));
                progressBar.setVisibility(View.GONE);


            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                progressBar.setVisibility(View.GONE);
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "Payment failed", Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()
                );
            }
        }
    }

    private void paymentData(String id, String status) {

        try {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("amount", totalAmount);
            jsonObject.addProperty("reg_id", reg_id);
            jsonObject.addProperty("id", id);
            jsonObject.addProperty("status", status);

            if (currencyType.equalsIgnoreCase("$")) {
                jsonObject.addProperty("currency", "usd");
            } else if (currencyType.equalsIgnoreCase("\u20ac")) {
                jsonObject.addProperty("currency", "euro");
            } else {
                jsonObject.addProperty("currency", "gbp");
            }
            Log.d(TAG, "paymentData: " + jsonObject);


            apiInterface.processDataSendConferencePaymentStatus(jsonObject).enqueue(new Callback<PaymentRegistration>() {
                @Override
                public void onResponse(@NotNull Call<PaymentRegistration> call, @NotNull Response<PaymentRegistration> response) {

                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        Log.d(TAG, "onResponse: " + response.body().isStatus());

                    } else {
                        Log.d(TAG, "onFailure:" + response.toString());
                    }


                }

                @Override
                public void onFailure(@NotNull Call<PaymentRegistration> call, @NotNull Throwable t) {

                    Log.d(TAG, "onFailure:" + t.toString());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
