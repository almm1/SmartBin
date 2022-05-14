package com.example.smart_bin.presentation.ui.verification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.smart_bin.databinding.VerificationtFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.*

class VerificationFragment : Fragment() {
    private var _binding: VerificationtFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: VerificationFragmentArgs by navArgs()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refDataBase: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = VerificationtFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        refDataBase = FirebaseDatabase.getInstance().reference

        binding.verifyButton.setOnClickListener { enterCode() }
    }

    private fun enterCode() {
        val code = binding.codeTextField.text.toString()
        val uid = args.id
        val phoneNumber = args.phoneNumber
        val credential = PhoneAuthProvider.getCredential(uid, code)

        mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                refDataBase.child("users").child(phoneNumber).child("id")
                    .setValue(mAuth.currentUser?.uid)
                findNavController().navigate(VerificationFragmentDirections.actionVerificationFragmentToHomeFragment())
            } else {
                Toast.makeText(
                    requireActivity(),
                    task.exception.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}