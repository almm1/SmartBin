package com.example.smart_bin.domain.repository

import com.example.smart_bin.presentation.model.Bonus

interface BonusRepository {
    fun get(onError: (String) -> Unit, onSuccess: (List<Bonus>) -> Unit)
}