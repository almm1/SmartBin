package com.example.smart_bin.domain.usecases.userusecases

import com.example.smart_bin.domain.repository.UserRepository

class LoadUserDataUseCase(private val userRepository: UserRepository) {
    fun execute(onSuccess:()->Unit){
        userRepository.load(onSuccess)
    }
}