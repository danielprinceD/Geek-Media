package com.example.geekmediaapp.Retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitService {
   @GET("/api/books") suspend fun getBookList() : Response<Library>
   @GET("/api/subject") suspend fun getSubject() : Response<Subject>
  @Multipart @POST("/api/upload")  fun postBook(
     @Part book : MultipartBody.Part ,
     @Part("name") filename : RequestBody ,
     @Part("description") description : RequestBody ,
     @Part("subject") subject : RequestBody
  ) : Call<Void>

   @POST("/api/update/book") suspend fun postSubject()
}