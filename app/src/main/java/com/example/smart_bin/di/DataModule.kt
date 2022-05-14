package com.example.smart_bin.di

import com.example.smart_bin.data.repository.AuthRepositoryImpl
import com.example.smart_bin.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    fun provideAuthRepository(auth: FirebaseAuth, database: FirebaseDatabase): AuthRepository {
        return AuthRepositoryImpl(auth, database)
    }
}