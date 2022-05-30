package com.example.smart_bin.presentation.ui.registration

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.canhub.cropper.*
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

    private var imageUri: Uri? = null

    @Inject
    lateinit var vmFactory: RegistrationViewModel.RegistrationViewModelFactory

    override fun setup() {
        (requireActivity().application as App).appComponent.injectRegistration(this)
        viewModel = ViewModelProvider(this, vmFactory)[RegistrationViewModel::class.java]

        val phoneNumber = args.phoneNumber
        binding.regPhonenumberText.text = phoneNumber

        binding.loadLogoUserImage.setOnClickListener {
            checkPermissions()
        }

        binding.regisrtationButton.setOnClickListener {
            val name = binding.nameTextField.text.toString()
            val surname = binding.surnameTextField.text.toString()
            if (name.isNotEmpty() && surname.isNotEmpty()) {
                viewModel.onRegistrationButtonClick(phoneNumber, name, surname, imageUri.toString(), requireActivity())
            } else showToast(getString(R.string.empty_field))
        }
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            imageUri = result.uriContent
            binding.loadLogoUserImage.setImageURI(imageUri)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("imageUri", imageUri.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val stringImage = savedInstanceState?.getString("imageUri")
        if (!stringImage.isNullOrEmpty()) {
            imageUri = Uri.parse(stringImage)
            binding.loadLogoUserImage.setImageURI(imageUri)
        }
    }

    private fun checkPermissions() {
        if (isPermissionGranted(CAMERA) && isPermissionGranted(READ_EXTERNAL_STORAGE)) {
            cropImageLaunch()
        } else {
            requestPermissionLauncher.launch(arrayOf(CAMERA, READ_EXTERNAL_STORAGE))
        }
    }


    private fun cropImageLaunch() {
        cropImage.launch(
            options {
                setAspectRatio(1, 1)
                setGuidelines(CropImageView.Guidelines.ON)
                setCropShape(CropImageView.CropShape.OVAL)
                setOutputCompressFormat(Bitmap.CompressFormat.PNG)
            }
        )
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        if (it[CAMERA] == true && it[READ_EXTERNAL_STORAGE] == true) cropImageLaunch()
        else showToast("Отсутствуют разрешения")

    }

    private fun isPermissionGranted(permission: String): Boolean =
        PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        )

}
