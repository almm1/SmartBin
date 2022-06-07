package com.example.smart_bin.domain.usecases.userusecases

import com.example.smart_bin.domain.repository.BonusRepository
import com.example.smart_bin.presentation.model.Bonus


class GetUserBonusesUseCase(private val bonusRepository: BonusRepository) {
    fun execute(onError: (String) -> Unit, onSuccess: (List<Bonus>) -> Unit) {
        bonusRepository.get(onError, onSuccess)
    }
}