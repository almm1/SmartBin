package com.example.smart_bin.presentation.ui.registration

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.smart_bin.App
import com.example.smart_bin.R
import com.example.smart_bin.databinding.RegistrationFragmentBinding
import com.example.smart_bin.presentation.base.BaseFragment
import javax.inject.Inject

class RegistrationFragment : BaseFragment<RegistrationFragmentBinding, RegistrationViewModel>() {

    private val args: RegistrationFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> RegistrationFragmentBinding =
        RegistrationFragmentBinding::inflate

    override lateinit var viewModel: RegistrationViewModel

    @Inject
    lateinit var vmFactory: RegistrationViewModel.RegistrationViewModelFactory

    override fun setup() {
        (requireActivity().application as App).appComponent.injectRegistration(this)
        viewModel = ViewModelProvider(this, vmFactory)[RegistrationViewModel::class.java]

        val phoneNumber = args.phoneNumber
        binding.textView.text = phoneNumber


        binding.regisrtationButton.setOnClickListener {
            val name = binding.nameTextField.text.toString()
            val surname = binding.surnameTextField.text.toString()
            if (name.isNotEmpty() && surname.isNotEmpty()) {
                viewModel.onRegistrationButtonClick(phoneNumber, name, surname, requireActivity())
            } else showToast(getString(R.string.empty_field))
        }
    }
}