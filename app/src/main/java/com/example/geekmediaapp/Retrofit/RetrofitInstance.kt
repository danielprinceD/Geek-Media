package com.example.geekmediaapp.Retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        const val BASEURL = "https://7bc9-2409-40f4-101d-9989-1d8e-ea33-216c-92b2.ngrok-free.app"
        fun getInstance() : Retrofit{
            return Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }
    }
}