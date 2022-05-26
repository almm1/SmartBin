package com.example.smart_bin.presentation.ui.registration

import android.Manifest.permission.CAMERA
import android.app.AlertDialog
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.canhub.cropper.*
import com.example.smart_bin.App
import com.example.smart_bin.R
import com.example.smart_bin.databinding.RegistrationFragmentBinding
import com.example.smart_bin.presentation.base.BaseFragment
import java.io.File
import javax.inject.Inject
import javax.xml.transform.Source


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
        binding.regPhonenumberText.text = phoneNumber

        binding.loadLogoUserImage.setOnClickListener {
            cropImage.launch(
                options {
                    setGuidelines(CropImageView.Guidelines.ON)
                    setCropShape(CropImageView.CropShape.OVAL)
                    setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                }
            )
        }

        binding.regisrtationButton.setOnClickListener {
            val name = binding.nameTextField.text.toString()
            val surname = binding.surnameTextField.text.toString()
            if (name.isNotEmpty() && surname.isNotEmpty()) {
                viewModel.onRegistrationButtonClick(phoneNumber, name, surname, requireActivity())
            } else showToast(getString(R.string.empty_field))
        }
    }

    private var imageUri: Uri? = null

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            imageUri = result.uriContent
            binding.loadLogoUserImage.setImageURI(imageUri)
//            uploadImage()
        } else {
            //showtoast
        }
    }
}
