package com.TRY.tryitonce.di

import com.TRY.tryitonce.data.repository.MockAuthRepositoryImpl
import com.TRY.tryitonce.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: MockAuthRepositoryImpl): AuthRepository
}
