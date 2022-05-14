package com.example.smart_bin.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.smart_bin.databinding.HomeFragmentBinding
import com.example.smart_bin.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refDataBase: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        refDataBase = FirebaseDatabase.getInstance().reference
        var user: User

        val phoneNumber = mAuth.currentUser?.phoneNumber.toString()

        refDataBase.child("users").child(phoneNumber).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               user = snapshot.getValue(User::class.java)?: User()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireActivity(), error.message, Toast.LENGTH_SHORT).show()
            }

        })
        binding.signOut.setOnClickListener {
            mAuth.signOut()
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWalkthroughFragment())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}