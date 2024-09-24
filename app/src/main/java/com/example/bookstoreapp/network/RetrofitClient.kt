package com.example.bookstoreapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Khởi tạo Retrofit với URL của backend Django
object RetrofitClient {
    private const val BASE_URL = "http://192.168.95.184:8000/api/users/"  // Địa chỉ cho Android Emulator

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Sử dụng GSON để chuyển đổi JSON
            .build()
            .create(ApiService::class.java)
    }
}
