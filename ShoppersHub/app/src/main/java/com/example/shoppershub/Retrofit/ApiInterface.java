package com.example.shoppershub.Retrofit;

import com.example.shoppershub.Model.CartModel;
import com.example.shoppershub.Model.LoginModel;
import com.example.shoppershub.Model.ProductsModel;
import com.example.shoppershub.Model.SignupModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("signup.php")
    Call<SignupModel> send_signup_data(
        @Field("email") String email,
        @Field("username") String username,
        @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginModel> login_verification(
        @Field("username") String username,
        @Field("password") String password
    );

    @FormUrlEncoded
    @POST("cart.php")
    Call<CartModel> cart_send_details(
            @Field("user_id") int user_id,
            @Field("product_id") int product_id,
            @Field("count") int count,
            @Field("colour") String colour,
            @Field("size") String size
    );

    @GET("products.php")
    Call<List<ProductsModel>> product_details();

    @GET("products_men.php")
    Call<List<ProductsModel>> product_men();

    @GET("products_women.php")
    Call<List<ProductsModel>> product_women();

    @GET("products_kids.php")
    Call<List<ProductsModel>> product_kids();

    @GET("cart.php")
    Call<List<CartModel>> cart_details(@Query("user_id") int user_id);

    @GET("cart.php")
    Call<CartModel> delete_cart(@Query("cart_id") int cart_id);
}