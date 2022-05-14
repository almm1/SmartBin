package com.example.smart_bin.presentation.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.smart_bin.databinding.RegistrationFragmentBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit

class RegistrationFragment : Fragment() {
    private var _binding: RegistrationFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: RegistrationFragmentArgs by navArgs()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refDataBase: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegistrationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        refDataBase = FirebaseDatabase.getInstance().reference

        val phoneNumber = args.phoneNumber
        binding.textView.text = phoneNumber
        binding.button2.setOnClickListener { registration(phoneNumber) }
    }

    private fun registration(phoneNumber: String) {
        val name = binding.textInputEditText.text.toString()
        val surname = binding.textInputEditText2.text.toString()

        if (name.isNotBlank() && surname.isNotBlank()) {

            val options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setActivity(requireActivity())
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

                    override fun onVerificationFailed(exception: FirebaseException) {
                        Toast.makeText(requireActivity(), exception.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                        super.onCodeSent(id, token)
                        val fullName = "$name $surname"
                        val userData = mutableMapOf<String, Any>()
                        userData["phone"] = phoneNumber
                        userData["full_name"] = fullName
                        userData["id"] = id

                        refDataBase.child("users").child(phoneNumber).updateChildren(userData)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    findNavController().navigate(
                                        RegistrationFragmentDirections.actionRegistrationFragmentToVerificationFragment(
                                            phoneNumber,
                                            id
                                        )
                                    )
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        it.exception.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                })
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)


        } else {
            Toast.makeText(requireActivity(), "Заполните поля", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}