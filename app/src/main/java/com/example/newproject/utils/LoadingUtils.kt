package com.example.newproject.utils

import android.app.Activity
import android.app.AlertDialog
import com.example.newproject.R

class LoadingUtils(val activity: Activity) {
    lateinit var alertDialog: AlertDialog

    fun show(){
        val  builder = AlertDialog.Builder(activity)
        val dialogview = activity.layoutInflater.inflate(R.layout.loading,null)
        builder.setView(dialogview)
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog.show()
    }

    fun dismiss(){
        alertDialog.dismiss()
    }

}