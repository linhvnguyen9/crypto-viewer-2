package com.linh.cryptoviewer2.presentation.di

import android.content.Context
import com.linh.cryptoviewer2.device.ConnectivityService
import com.linh.cryptoviewer2.device.ConnectivityServiceImpl
import com.linh.cryptoviewer2.presentation.util.ResourceProvider
import com.linh.cryptoviewer2.presentation.util.ResourceProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return ResourceProviderImpl(context)
    }
}