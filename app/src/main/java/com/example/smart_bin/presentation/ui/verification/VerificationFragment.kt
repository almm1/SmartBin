package com.example.smart_bin.presentation.ui.verification

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.smart_bin.App
import com.example.smart_bin.databinding.VerificationtFragmentBinding
import com.example.smart_bin.presentation.base.BaseFragment
import com.google.android.material.textfield.TextInputEditText
import javax.inject.Inject

class VerificationFragment : BaseFragment<VerificationtFragmentBinding, VerificationViewModel>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VerificationtFragmentBinding =
        VerificationtFragmentBinding::inflate

    override lateinit var viewModel: VerificationViewModel

    @Inject
    lateinit var vmFactory: VerificationViewModel.VerificationViewModelFactory

    private val args: VerificationFragmentArgs by navArgs()
    private var code: String = ""
    private var codeLength = 0

    override fun setup() {
        (requireActivity().application as App).appComponent.injectVerification(this)
        viewModel = ViewModelProvider(this, vmFactory)[VerificationViewModel::class.java]

        binding.message.text =
            "Введите 6-значный код, который был отправлен на номер телефона ${args.regUser.phone}"
        binding.verifyButton.isEnabled = false

        binding.verifyButton.setOnClickListener {
            val regUser = args.regUser
            val c = code
            viewModel.onVerifyButtonClick(
                code,
                regUser
            )
        }


        binding.editText.codeTextField1.onChangeTextListener(nextView = binding.editText.codeTextField2)
        binding.editText.codeTextField2.onChangeTextListener(
            nextView = binding.editText.codeTextField3,
        )
        binding.editText.codeTextField3.onChangeTextListener(
            nextView = binding.editText.codeTextField4,
        )
        binding.editText.codeTextField4.onChangeTextListener(
            nextView = binding.editText.codeTextField5,
        )
        binding.editText.codeTextField5.onChangeTextListener(
            nextView = binding.editText.codeTextField6,
        )
        binding.editText.codeTextField6.onChangeTextListener(
            nextView = binding.editText.codeTextField6,
        )


        setOnKeyEvent(binding.editText.codeTextField6, binding.editText.codeTextField5)
        setOnKeyEvent(binding.editText.codeTextField5, binding.editText.codeTextField4)
        setOnKeyEvent(binding.editText.codeTextField4, binding.editText.codeTextField3)
        setOnKeyEvent(binding.editText.codeTextField3, binding.editText.codeTextField2)
        setOnKeyEvent(binding.editText.codeTextField2, binding.editText.codeTextField1)
        setOnKeyEvent(binding.editText.codeTextField1, binding.editText.codeTextField1)


    }

    private fun setOnKeyEvent(view: TextInputEditText, prevView: TextInputEditText) {
        view.setOnKeyListener { _, i, _ ->
            if (i == KeyEvent.KEYCODE_DEL) {
                code = code.dropLast(1)
                codeLength--
                prevView.requestFocus()
                prevView.text?.clear()
            }
            false
        }
    }


    private fun TextInputEditText.onChangeTextListener(
        nextView: TextInputEditText? = null
    ) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun onTextChanged(
                p0: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (count > before) {
                    nextView?.requestFocus()
                    codeLength++
                }
                binding.verifyButton.isEnabled = codeLength == 6
            }

            override fun afterTextChanged(p0: Editable?) {
                code += p0
            }
        })
    }
}