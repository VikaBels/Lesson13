package com.example.lesson13

interface CustomDialogConfigurationListener {

    fun onNewConfiguration(title:String,message:String, cancel: Boolean)
}