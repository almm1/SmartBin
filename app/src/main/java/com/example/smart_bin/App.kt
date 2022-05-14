package com.example.smart_bin

import android.app.Application
import com.example.smart_bin.di.AppComponent
import com.example.smart_bin.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}