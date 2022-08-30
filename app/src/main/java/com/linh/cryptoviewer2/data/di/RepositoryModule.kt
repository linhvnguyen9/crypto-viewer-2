package com.linh.cryptoviewer2.data.di

import com.linh.cryptoviewer2.data.repository.CoinRepositoryImpl
import com.linh.cryptoviewer2.data.repository.SearchRepositoryImpl
import com.linh.cryptoviewer2.data.repository.WatchlistRepositoryImpl
import com.linh.cryptoviewer2.domain.repository.CoinRepository
import com.linh.cryptoviewer2.domain.repository.SearchRepository
import com.linh.cryptoviewer2.domain.repository.WatchlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    @Binds
    fun bindSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository

    @Binds
    fun bindWatchlistRepositoryImpl(watchlistRepositoryImpl: WatchlistRepositoryImpl): WatchlistRepository
}