package com.example.fallingaccident.di.main

import com.example.fallingaccident.ui.main.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MainViewModelBindModule {

    @Binds
    abstract fun bindMainIntentProcessor(
        intentProcessor: MainIntentProcessor
    ): MainProcessor

    @Binds
    abstract fun bindMainActionReducer(
        actionReducer: MainActionReducerImpl
    ): MainActionReducer
}