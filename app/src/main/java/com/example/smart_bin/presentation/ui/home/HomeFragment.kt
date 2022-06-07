package com.example.smart_bin.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.smart_bin.App
import com.example.smart_bin.databinding.HomeFragmentBinding
import com.example.smart_bin.presentation.base.BaseFragment
import com.example.smart_bin.presentation.model.user
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> HomeFragmentBinding =
        HomeFragmentBinding::inflate

    override lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var vmFactory: HomeViewModel.HomeViewModelFactory

    override fun setup() {
        (requireActivity().application as App).appComponent.injectHome(this)
        viewModel = ViewModelProvider(this, vmFactory)[HomeViewModel::class.java]

        viewModel.initUser {
            if (user.icon.isNotEmpty()) {
                Glide.with(requireContext())
                    .load(user.icon)
                    .into(binding.logoUser)
            }
        }
        binding.logoUser.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
        }
    }
}