package com.linh.cryptoviewer2.data.di

import com.linh.cryptoviewer2.data.repository.CoinRepositoryImpl
import com.linh.cryptoviewer2.domain.repository.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository
}