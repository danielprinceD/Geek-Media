package com.example.geekmediaapp

import android.content.Context
import android.widget.Toast

class Toaster {
    companion object{
        fun toast(c : Context , m : String){

            Toast.makeText(c , m, Toast.LENGTH_LONG).show()
        }

    }
}