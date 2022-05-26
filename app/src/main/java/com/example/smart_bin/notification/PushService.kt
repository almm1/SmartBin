package com.example.smart_bin.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("new token", token)
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("TAG", "From: ${remoteMessage.from}")

        remoteMessage.data.let {
            Log.d("TAG", "Message data payload: " + remoteMessage.data)
        }

    }
}