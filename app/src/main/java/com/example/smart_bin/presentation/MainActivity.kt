package com.example.smart_bin.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import com.example.smart_bin.R
import com.example.smart_bin.databinding.AlertDialogBinding
import com.example.smart_bin.databinding.MainActivityBinding
import com.example.smart_bin.notification.PushService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private lateinit var navController: NavController
    private lateinit var pushBroadcastReceiver: BroadcastReceiver

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHost = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHost.navController

        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            if (intent.extras != null) {
                addBonus(intent.extras!!)
                navController.navigate(R.id.action_global_bonusesFragment)
            } else {
                navController.navigate(R.id.action_global_homeFragment)
            }
        } else {
            navController.navigate(R.id.walkthroughFragment)
        }

        regBroadcastReceiver()
    }

    private fun regBroadcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(PushService.INTENT_FILTER)
        pushBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, intent: Intent?) {
                addBonus(intent?.extras!!)
                showCustomAlert()
            }
        }
        registerReceiver(pushBroadcastReceiver, intentFilter)
    }


    private fun showCustomAlert() {
        val dialogView = layoutInflater.inflate(R.layout.alert_dialog, null)
        val binding = AlertDialogBinding.bind(dialogView)
        try {
            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .show()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            binding.okButton.setOnClickListener { dialog.dismiss() }
            binding.viewButton.setOnClickListener {
                dialog.dismiss()
                navController.navigate(R.id.action_global_bonusesFragment)
            }
        } catch (e: Exception) {
        }
    }

    private fun addBonus(extras: Bundle) {
        val data = mutableMapOf<String, Any>()
        data["body"] = extras["body"] as Any
        data["title"] = extras["title"] as Any
        data["promocode"] = extras["promocode"] as Any
        data["is_used"] = false

        val db = FirebaseDatabase.getInstance().reference
        db.child("bonuses").child(mAuth.currentUser?.phoneNumber.toString())
            .child(extras["promocode"].toString())
            .setValue(data)
    }
}