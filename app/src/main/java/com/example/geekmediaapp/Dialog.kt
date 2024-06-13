package com.example.geekmediaapp

import android.app.Activity
import android.app.AlertDialog
import android.app.DialogFragment
import android.view.LayoutInflater

class Dialog (val activity : Activity) {
    private lateinit var isDialog : AlertDialog
    fun startDialog(){
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.dialog_layout, null))
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }
    fun dismiss(){
            isDialog.dismiss()
    }
}