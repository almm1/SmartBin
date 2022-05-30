package com.example.smart_bin.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegUser(
    val id: String,
    val phone: String,
    val full_name: String? = null,
    val image: String? = null
) : Parcelable