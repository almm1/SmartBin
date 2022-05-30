package com.example.smart_bin.presentation.ui.verification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.smart_bin.App
import com.example.smart_bin.databinding.VerificationtFragmentBinding
import com.example.smart_bin.presentation.base.BaseFragment
import javax.inject.Inject

class VerificationFragment : BaseFragment<VerificationtFragmentBinding, VerificationViewModel>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VerificationtFragmentBinding =
        VerificationtFragmentBinding::inflate

    override lateinit var viewModel: VerificationViewModel

    @Inject
    lateinit var vmFactory: VerificationViewModel.VerificationViewModelFactory

    private val args: VerificationFragmentArgs by navArgs()

    override fun setup() {
        (requireActivity().application as App).appComponent.injectVerification(this)
        viewModel = ViewModelProvider(this, vmFactory)[VerificationViewModel::class.java]


        binding.verifyButton.setOnClickListener {
            val code = binding.codeTextField.text.toString()
            val regUser = args.regUser

            viewModel.onVerifyButtonClick(
                code,
                regUser
            )
        }
    }
}