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

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("TAG", token)
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

        appComponent = DaggerAppComponent.create()
    }
}