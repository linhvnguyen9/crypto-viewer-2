package com.linh.cryptoviewer2.device.di

import android.content.Context
import com.linh.cryptoviewer2.device.ConnectivityService
import com.linh.cryptoviewer2.device.ConnectivityServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeviceModule {

    @Provides
    @Singleton
    fun provideConnectivityService(@ApplicationContext context: Context): ConnectivityService {
        return ConnectivityServiceImpl(context)
    }
}