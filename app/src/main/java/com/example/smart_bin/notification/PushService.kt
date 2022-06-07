package com.example.smart_bin.notification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("TAG", "From: ${remoteMessage.from}")

        val intent = Intent(INTENT_FILTER)
        remoteMessage.data.forEach { entity ->
            intent.putExtra(entity.key, entity.value)
        }
        sendBroadcast(intent)

        remoteMessage.data.let {
            Log.d("TAG", "Message data payload: " + remoteMessage.data)
        }
    }

    companion object {
        const val INTENT_FILTER = "PUSH_INTENT"
    }
}