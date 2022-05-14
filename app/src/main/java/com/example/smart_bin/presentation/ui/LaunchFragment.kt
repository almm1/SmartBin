package com.example.smart_bin.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class LaunchFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            findNavController()
                .navigate(LaunchFragmentDirections.actionLaunchFragmentToLoginFragment())
        } else {
            findNavController()
                .navigate(LaunchFragmentDirections.actionLaunchFragmentToWalkthroughFragment())
        }
    }
}