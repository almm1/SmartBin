package com.example.smart_bin

import android.app.Application
import android.util.Log
import com.example.smart_bin.di.AppComponent
import com.example.smart_bin.di.DaggerAppComponent
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class App : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.create()
    }
}