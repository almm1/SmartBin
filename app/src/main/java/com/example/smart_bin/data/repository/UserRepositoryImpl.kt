package com.example.smart_bin.data.repository

import com.example.smart_bin.domain.repository.UserRepository
import com.example.smart_bin.presentation.model.User
import com.example.smart_bin.presentation.model.user
import com.example.smart_bin.utils.AppValueEventListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class UserRepositoryImpl(
    private val mAuth: FirebaseAuth,
    private val database: FirebaseDatabase,
    private val storage: FirebaseStorage
) : UserRepository {
    override fun load(onSuccess:()->Unit) {
        val phoneNumber = mAuth.currentUser?.phoneNumber.toString()
        database.reference.child("users").child(phoneNumber)
            .addListenerForSingleValueEvent(AppValueEventListener {
                user = it.getValue(User::class.java) ?: User()
                storage.reference.child("userImage")
                    .child(user.id).downloadUrl.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val photoUrl = task.result.toString()
                        user.icon = photoUrl
                        onSuccess()
                    }
                }
            })
    }
}