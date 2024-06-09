package com.example.geekmediaapp

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.geekmediaapp.Retrofit.Library
import com.example.geekmediaapp.Retrofit.RetrofitInstance
import com.example.geekmediaapp.Retrofit.RetrofitService
import com.example.geekmediaapp.Retrofit.Subject
import retrofit2.Response
import java.security.acl.Owner

class MyViewModel : ViewModel() {
    private val serviceInstance: RetrofitService = RetrofitInstance.getInstance().create(RetrofitService::class.java)
    private var arr : Array<String> = arrayOf("Select Subject" ,"Python" , "C++" , "Java")
    private val bookResponse : LiveData<Response<Library>> = liveData {
        val res = serviceInstance.getBookList()
        emit(res)
    }
     fun getSubjectList() : Array<String> {
        return arr
    }
    fun getBookList() : LiveData<Response<Library>>{
        return bookResponse
    }

}