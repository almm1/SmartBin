package com.example.smart_bin.data.repository

import com.example.smart_bin.domain.repository.BonusRepository
import com.example.smart_bin.presentation.model.Bonus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BonusRepositoryImpl(
    private val mAuth: FirebaseAuth,
    private val database: FirebaseDatabase
) : BonusRepository {

    override fun get(onError: (String) -> Unit, onSuccess: (List<Bonus>) -> Unit) {
        val phone = mAuth.currentUser?.phoneNumber.toString()
        database.reference.child("bonuses").child(phone)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val bonus = snapshot.children.map {
                            val b = it.getValue(Bonus::class.java) ?: Bonus()
                            b.is_used = it.child("is_used").value as Boolean
                            b
                        }
                        onSuccess(bonus)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        onError(error.message)
                    }

                }
            )
    }
}