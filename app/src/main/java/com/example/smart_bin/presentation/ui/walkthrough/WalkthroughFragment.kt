package com.example.smart_bin.presentation.ui.walkthrough

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.smart_bin.databinding.WalkthroughFragmentBinding
import com.example.smart_bin.presentation.base.BaseFragment

class WalkthroughFragment : BaseFragment<WalkthroughFragmentBinding, WalkthroughViewModel>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> WalkthroughFragmentBinding =
        WalkthroughFragmentBinding::inflate
    override lateinit var viewModel: WalkthroughViewModel

    override fun setup() {
        viewModel=WalkthroughViewModel()
        binding.startButton.setOnClickListener {
            viewModel.onStartButtonClick()
        }
    }
}