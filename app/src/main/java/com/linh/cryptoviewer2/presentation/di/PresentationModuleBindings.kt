package com.linh.cryptoviewer2.presentation.di

import com.linh.cryptoviewer2.presentation.navigation.Navigator
import com.linh.cryptoviewer2.presentation.navigation.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PresentationModuleBindings {

    @Binds
    @Singleton
    fun bindNavigator(impl: NavigatorImpl): Navigator
}