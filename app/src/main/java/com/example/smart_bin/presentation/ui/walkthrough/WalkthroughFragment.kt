package com.example.smart_bin.presentation.ui.walkthrough

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.smart_bin.R
import com.example.smart_bin.databinding.WalkthroughFragmentBinding
import com.example.smart_bin.presentation.base.BaseFragment

class WalkthroughFragment : BaseFragment<WalkthroughFragmentBinding, WalkthroughViewModel>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> WalkthroughFragmentBinding =
        WalkthroughFragmentBinding::inflate
    override lateinit var viewModel: WalkthroughViewModel

    override fun setup() {
        viewModel = WalkthroughViewModel()

        val spannable = SpannableString("smartbin")
        spannable.setSpan(
            ForegroundColorSpan(requireContext().getColor(R.color.green_base)),
            5, 8,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.titleText.text = spannable
        binding.startButton.setOnClickListener {
            viewModel.onStartButtonClick()
        }
    }
}