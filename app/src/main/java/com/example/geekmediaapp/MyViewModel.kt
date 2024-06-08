package com.example.geekmediaapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.geekmediaapp.Retrofit.RetrofitInstance
import com.example.geekmediaapp.Retrofit.RetrofitService
import com.example.geekmediaapp.Retrofit.Subject
import retrofit2.Response

class MyViewModel : ViewModel() {
    private val serviceInstance: RetrofitService = RetrofitInstance.getInstance().create(RetrofitService::class.java)
    private var arr : Array<String> = arrayOf("Select Subject" ,"Python" , "C++" , "Java")
     fun getSubjectList() : Array<String> {
        return arr
    }
}