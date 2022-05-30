package com.example.smart_bin.di

import com.example.smart_bin.data.api.NewsNetworkService
import com.example.smart_bin.data.datasource.NewsDataSource
import com.example.smart_bin.data.datasource.NewsPageSource
import com.example.smart_bin.data.mappers.NewsApiResponseMapper
import com.example.smart_bin.data.repository.AuthRepositoryImpl
import com.example.smart_bin.data.repository.NewsRepositoryImpl
import com.example.smart_bin.domain.repository.AuthRepository
import com.example.smart_bin.domain.repository.NewsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Singleton
    @Provides
    fun provideNetworkService(): NewsNetworkService {
        return NewsNetworkService
    }

    @Singleton
    @Provides
    fun provideNewsPageSource(dataSource: NewsDataSource): NewsPageSource {
        return NewsPageSource(dataSource)
    }

    @Singleton
    @Provides
    fun provideNewsDataSource(
        service: NewsNetworkService,
        mapper: NewsApiResponseMapper
    ): NewsDataSource {
        return NewsDataSource(service.retrofit, mapper)
    }

    @Singleton
    @Provides
    fun provideNewsApiResponseMapper(): NewsApiResponseMapper {
        return NewsApiResponseMapper()
    }
}