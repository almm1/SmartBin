package com.example.smart_bin.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.smart_bin.databinding.ProfileFragmentBinding
import com.example.smart_bin.presentation.model.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (user.icon.isNotEmpty()) {
            Glide.with(requireContext())
                .load(user.icon)
                .into(binding.logoUser)
        }
        binding.phonenumberText.text = user.phone
        binding.fullnameText.text = user.full_name

        binding.bonus.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToBonusesFragment())
        }
        binding.map.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToMapFragment())
        }
        binding.about.setOnClickListener {
            Toast.makeText(requireContext(), "О нас", Toast.LENGTH_LONG).show()
        }
        binding.stats.setOnClickListener {
            Toast.makeText(requireContext(), "Статистика", Toast.LENGTH_LONG).show()
        }
        binding.qusts.setOnClickListener {
            Toast.makeText(requireContext(), "Квесты", Toast.LENGTH_LONG).show()
        }
        binding.help.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHelpFragment())
        }
        binding.signOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(ProfileFragmentDirections.actionProfileToWalkthroughFragment())
        }
    }
}