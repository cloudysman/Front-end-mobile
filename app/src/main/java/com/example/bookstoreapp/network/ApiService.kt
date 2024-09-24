package com.example.bookstoreapp.network

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.GET
// Interface chứa các yêu cầu HTTP đến API
interface ApiService {

    // API đăng ký
    @FormUrlEncoded
    @POST("register/")
    fun registerUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ApiResponse>

    // API đăng nhập
    @FormUrlEncoded
    @POST("login/")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ApiResponse>
    @GET("books/")
    fun getBooks(): Call<List<Book>>
}
