package com.example.fallingaccident.di.main

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.fallingaccident.ui.main.MainActionDispatcher
import com.example.fallingaccident.ui.main.MainActionDispatcherImpl
import com.example.fallingaccident.ui.main.MainViewModelDelegate
import com.example.fallingaccident.ui.main.MainViewModelDelegateImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideMainViewModelDelegate(
        mainViewModelDelegate: MainViewModelDelegateImpl
    ): MainViewModelDelegate = mainViewModelDelegate

    @Singleton
    @Provides
    fun provideMainActionDispatcher(
        mainActionDispatcher: MainActionDispatcherImpl
    ): MainActionDispatcher = mainActionDispatcher
}