package com.linh.cryptoviewer2.data.di

import com.linh.cryptoviewer2.data.local.dao.WatchlistDao
import com.linh.cryptoviewer2.data.local.dao.WatchlistDaoImpl
import com.linh.cryptoviewer2.data.local.model.WatchlistItemLocal
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideRealm(): Realm {
        val config = RealmConfiguration.Builder(schema = setOf(WatchlistItemLocal::class)).build()
        return Realm.open(config)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface LocalBindingsModule {

    @Binds
    fun bindWatchListItemDao(watchListItemDao: WatchlistDaoImpl): WatchlistDao
}