package com.applications.euroscicon.api;


import com.applications.euroscicon.models.AbsrtactResponse;
import com.applications.euroscicon.models.BaseResponse;
import com.applications.euroscicon.models.BrochureDownload;
import com.applications.euroscicon.models.ConferenceBanner;
import com.applications.euroscicon.models.ConferenceProducts;
import com.applications.euroscicon.models.Events;
import com.applications.euroscicon.models.FilterData;
import com.applications.euroscicon.models.PaymentRegistration;
import com.applications.euroscicon.models.PaymentStripe;
import com.applications.euroscicon.models.RegistrationsListResponse;
import com.applications.euroscicon.models.Sessions;
import com.applications.euroscicon.models.SubmitAbstract;
import com.applications.euroscicon.models.SubmitAudioVideo;
import com.applications.euroscicon.models.Template;
import com.applications.euroscicon.models.TrackName;
import com.applications.euroscicon.models.UserCreation;
import com.applications.euroscicon.models.UserLogin;
import com.google.gson.JsonObject;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Pagination
 * Created by Suleiman19 on 10/27/16.
 * Copyright (c) 2016. Suleiman Ali Shakir. All rights reserved.
 */

public interface ApiInterface {



    @Headers("Content-Type: application/json")
    @POST("api/get/mobiledata_all")
    Call<Events> processDataAll(@Body JsonObject body);


    @Headers("Content-Type: application/json")
    @POST("api/get/mobiledata_conference")
    Call<ConferenceBanner> processDataConference(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("api/conference/get_registration_products")
    Call<ConferenceProducts> processDataConferenceProducts(@Body JsonObject body);


    @Headers("Content-Type: application/json")
    @POST("stripe/public/create-payment-intent.php")
    Call<PaymentStripe> processDataConferencePayment(@Body JsonObject body);


    @Headers("Content-Type: application/json")
    @POST("api/conference/insert_registration")
    Call<PaymentRegistration> processDataSendConferencePayment(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("api/conference/update_registration")
    Call<PaymentRegistration> processDataSendConferencePaymentStatus(@Body JsonObject body);



    @Headers("Content-Type: application/json")
    @POST("api/conference/get_tracks")
    Call<TrackName> processDataGetTrackName(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("api/conference/get_abstract_template_file")
    Call<Template> processDataGetTemplate(@Body JsonObject body);


    @Headers("Content-Type: application/json")
    @POST("api/conference/insert_app_user")
    Call<UserCreation> processDataInsertAppUser(@Body JsonObject body);


    @Headers("Content-Type: application/json")
    @POST("api/conference/app_user_login")
    Call<UserLogin> processDataAppUser(@Body JsonObject body);

    @Multipart
    @POST("api/conference/insert_abstract")
    Call<SubmitAbstract> processDataSendAbstract(
            @Part MultipartBody.Part file,
            @Part("conf_id") RequestBody conf_id,
            @Part("title") RequestBody title,
            @Part("name") RequestBody name,
            @Part("country") RequestBody country,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("category") RequestBody category,
            @Part("track_name") RequestBody track_name,
            @Part("address") RequestBody address,
            @Part("date") RequestBody date,
            @Part("app_user_id") RequestBody app_user_id,
            @Part("source") RequestBody source);



    @Multipart
    @POST("api/conference/audio_video_submission")
    Call<SubmitAudioVideo> processDataSendAudioVideo(
            @Part MultipartBody.Part file,
            @Part("conf_id") RequestBody conf_id,
            @Part("title") RequestBody title,
            @Part("name") RequestBody name,
            @Part("country") RequestBody country,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("address") RequestBody address,
            @Part("date") RequestBody date,
            @Part("app_user_id") RequestBody app_user_id,
            @Part("source") RequestBody source);



    @Headers("Content-Type: application/json")
    @POST("api/conference/brochure_download")
    Call<BrochureDownload> processDataBrochureDownload(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("api/get/mobiledata_insert_subscription")
    Call<BaseResponse> processDataInsertSubscription(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("api/conference/get_filters")
    Call<FilterData> processDataCountryandCity(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("api/conference/get_track_details")
    Call<Sessions> processDataSessionsandTracks(@Body JsonObject body);


    @Headers("Content-Type: application/json")
    @POST("api/conference/mobiledata_insert_feedback")
    Call<BaseResponse> processDataFeedBack(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("api/conference/abstracts")
    Call<AbsrtactResponse> processDataAbstractList(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("api/conference/registrations")
    Call<RegistrationsListResponse> processDataRegistrationsList(@Body JsonObject body);
}
