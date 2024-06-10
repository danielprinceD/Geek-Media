package com.example.geekmediaapp.Retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        const val BASEURL = "https://9468-2409-40f4-102f-bdfa-9df4-aad5-1e48-d0bc.ngrok-free.app"
        fun getInstance() : Retrofit{
            return Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }
    }
}