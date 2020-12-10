package com.example.login_restapi.api;

import com.example.login_restapi.models.DefaultResponse;
import com.example.login_restapi.activities.Login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("createUser")
    Call<DefaultResponse> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name,
            @Field("school") String school
    );

    @FormUrlEncoded
    @POST("userLogin")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );
//
//    @GET("allusers")
//    Call<UsersResponse> getUsers();
//
//    @FormUrlEncoded
//    @PUT("updateuser/{id}")
//    Call<LoginResponse> updateUser(
//            @Path("id") int id,
//            @Field("email") String email,
//            @Field("name") String name,
//            @Field("school") String school
//    );
//
//    @FormUrlEncoded
//    @PUT("updatepassword")
//    Call<DefaultResponse> updatePassword(
//            @Field("currentpassword") String currentpassword,
//            @Field("newpassword") String newpassword,
//            @Field("email") String email
//    );
//
//    @DELETE("deleteuser/{id}")
//    Call<DefaultResponse> deleteUser(@Path("id") int id);
}
