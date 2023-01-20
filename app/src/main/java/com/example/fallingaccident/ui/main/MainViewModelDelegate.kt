package com.example.fallingaccident.ui.main

import com.example.fallingaccident.ui.viewmodel.ViewModelDelegate
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

interface MainViewModelDelegate : ViewModelDelegate<Intention, Action>

class MainViewModelDelegateImpl @Inject constructor() : MainViewModelDelegate {
    override val intents: MutableSharedFlow<Intention> = MutableSharedFlow()

    override suspend fun processIntent(intent: Intention) = intents.emit(intent)
}