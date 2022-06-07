package com.example.smart_bin.di

import com.example.smart_bin.data.datasource.NewsDataSource
import com.example.smart_bin.data.repository.AuthRepositoryImpl
import com.example.smart_bin.data.repository.BonusRepositoryImpl
import com.example.smart_bin.data.repository.NewsRepositoryImpl
import com.example.smart_bin.data.repository.UserRepositoryImpl
import com.example.smart_bin.domain.repository.AuthRepository
import com.example.smart_bin.domain.repository.BonusRepository
import com.example.smart_bin.domain.repository.NewsRepository
import com.example.smart_bin.domain.repository.UserRepository
import com.example.smart_bin.domain.usecases.GetNewsListUseCase
import com.example.smart_bin.domain.usecases.authusecases.*
import com.example.smart_bin.domain.usecases.userusecases.GetUserBonusesUseCase
import com.example.smart_bin.domain.usecases.userusecases.LoadUserDataUseCase
import com.example.smart_bin.domain.usecases.userusecases.UserUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideAuthUseCases(repository: AuthRepository) = AuthUseCases(
        firebaseSendCode = FirebaseSendCode(repository),
        firebaseSignInUseCase = FirebaseSignInUseCase(repository),
        firebaseSignOutUseCase = FirebaseSignOutUseCase(repository),
        firebaseSignUpUseCase = FirebaseSignUpUseCase(repository),
        firebaseUserIsRegistered = FirebaseUserIsRegistered(repository)
    )

    @Singleton
    @Provides
    fun provideUserUseCases(userRepository: UserRepository, bonusRepository: BonusRepository) =
        UserUseCases(
            loadUserDataUseCase = LoadUserDataUseCase(userRepository),
            getUserBonusesUseCase = GetUserBonusesUseCase(bonusRepository)
        )


    @Singleton
    @Provides
    fun provideNewsUseCase(newsRepository: NewsRepository): GetNewsListUseCase {
        return GetNewsListUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideNewsRepository(dataSource: NewsDataSource): NewsRepository {
        return NewsRepositoryImpl(dataSource)
    }

    @Singleton
    @Provides
    fun provideBonusRepository(
        auth: FirebaseAuth,
        database: FirebaseDatabase
    ): BonusRepository {
        return BonusRepositoryImpl(auth, database)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        auth: FirebaseAuth,
        database: FirebaseDatabase,
        storage: FirebaseStorage
    ): UserRepository {
        return UserRepositoryImpl(auth, database, storage)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        database: FirebaseDatabase,
        storage: FirebaseStorage
    ): AuthRepository {
        return AuthRepositoryImpl(auth, database, storage)
    }

}