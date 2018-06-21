package com.cog.Dropinn.Models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by test on 7/10/17.
 **/

public interface RetrofitArrayAPI {
    /*
                                    - - Example - -
    http://demo.cogzidel.com/dropinn416/mobile/user/login?email_id=test@test.com&password=password

    @GET("login")
    Call<List<LoginModel>> doLogin(
            @Query("email_id") String email_id, @Query("password") String password);
    */

    @GET("login")
    Call<List<LoginModel>> doLogin(
            @Query("email_id") String email_id, @Query("password") String password);

    @GET("email_exists")
    Call<List<EmailExistModel>> isEmailExist(
            @Query("email") String email_id);

    @GET("signup")
    Call<List<SignupModel>> doSignup(
            @Query("firstname") String firstname, @Query("lastname") String lastname, @Query("email_id") String email_id, @Query("password") String password, @Query("join_date") String join_date);

    @GET("forgot_password")
    Call<List<ForgotPasswordModel>> sentForgotPassword(@Query("email") String email);

    @GET("fb_signup")
    Call<List<FacebookSignupModel>> doFacebookSignup(
            @Query("fname") String fbFirstName, @Query("lname") String fbLastName, @Query("email_id") String fbEmail, @Query("fb_id") String fbID,
            @Query("live") String live, @Query("work") String work, @Query("phnum") String phnum, @Query("describe") String describe, @Query("src") String src,
            @Query("join_date") String join_date);

    @GET("view_profile")
    Call<List<ViewProfileModel>> viewProfile(
            @Query("user_id") String user_id, @Query("email") String email);

    @GET("search_results")
    Call<List<ExploreResultModel>>explore(
            @Query("guests") String guest, @Query("room_types") String roomtypes, @Query("price_min") String pricemin, @Query("price_max") String pricemax, @Query("min_beds") String minbeds,
            @Query("min_bedrooms") String minbedrooms,@Query("min_bathrooms") String minbathrooms, @Query("location") String location, @Query("start") String start, @Query("checkin") String checkin, @Query("checkout") String checkout,
            @Query("common_currency") String commoncurrency, @Query("keywords") String keywords
    );
    @GET("apartment_detail")
    Call<List<DetailsModel>>details(
            @Query("roomid") String roomid,@Query("common_currency") String common_currency
    );

}
