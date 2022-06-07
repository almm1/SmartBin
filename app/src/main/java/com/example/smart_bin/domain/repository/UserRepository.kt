package com.example.smart_bin.domain.repository

interface UserRepository {
    fun load(onSuccess: () -> Unit)
}