package com.linh.cryptoviewer2.device.di

import android.content.Context
import com.linh.cryptoviewer2.device.ConnectivityService
import com.linh.cryptoviewer2.device.ConnectivityServiceImpl
import com.linh.cryptoviewer2.device.notification.NotificationManagerImpl
import com.linh.cryptoviewer2.device.work.WorkRequestManagerImpl
import com.linh.cryptoviewer2.domain.device.NotificationManager
import com.linh.cryptoviewer2.domain.device.WorkRequestManager
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

@Module
@InstallIn(SingletonComponent::class)
interface DeviceModuleBindingsModule {

    @Binds
    fun bindNotificationManager(impl: NotificationManagerImpl): NotificationManager

    @Binds
    fun bindWorkRequestManager(impl: WorkRequestManagerImpl): WorkRequestManager
}