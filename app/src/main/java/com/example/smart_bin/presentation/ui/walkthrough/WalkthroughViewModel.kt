package com.example.smart_bin.presentation.ui.walkthrough

import com.example.smart_bin.presentation.base.BaseViewModel

class WalkthroughViewModel : BaseViewModel() {

    fun onStartButtonClick() {
        navigate(WalkthroughFragmentDirections.actionWalkthroughFragmentToLoginFragment())
    }

}