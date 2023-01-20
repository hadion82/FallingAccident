package com.example.fallingaccident

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fallingaccident.ui.main.*
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.fallingaccident.ui.main.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    mainViewModelDelegate: MainViewModelDelegate,
    processor: MainProcessor,
    dispatcher: MainActionDispatcher,
    reducer: MainActionReducer
) : ViewModel(),
    MainViewModelDelegate by mainViewModelDelegate,
    MainProcessor by processor,
    MainActionDispatcher by dispatcher,
    MainActionReducer by reducer {

    init {
        intents().onEach(::process)
            .launchIn(viewModelScope)
    }

    val viewState = actions()
        .scan(UiState.idle(), ::reduce)
        .stateIn(viewModelScope, SharingStarted.Eagerly, UiState.idle())


    private fun intents(): Flow<Intention> {
        val start = intents.filterIsInstance<Intention.Effect.Sensor.Start>()
        val stop = intents.filterIsInstance<Intention.Effect.Sensor.Stop>()
        val message = intents.filterIsInstance<Intention.Action.Message>()
        return merge(start, stop, message)
    }
}