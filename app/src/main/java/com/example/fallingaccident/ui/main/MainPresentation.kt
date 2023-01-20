package com.example.fallingaccident.ui.main

import com.example.shared.reducer.ActionReducer
import com.example.shared.functional.Event
import com.example.shared.sensor.Falling
import javax.inject.Inject

sealed interface Intention {
    sealed interface Effect : Intention {
        sealed interface Sensor : Effect {
            object Start : Sensor
            object Stop : Sensor
        }
    }

    sealed interface Action : Intention {
        sealed interface Message : Action {
            object State : Message
        }
    }
}

sealed interface Action {

    data class Update(val falling: Falling) : Action {
        override fun reduce(state: UiState) =
            state.copy(falling = falling)
    }

    data class Message(val message: CharSequence?) : Action {
        override fun reduce(state: UiState): UiState =
            state.copy(message = Event(message))
    }

    fun reduce(state: UiState): UiState
}

interface MainActionReducer : ActionReducer<UiState, Action>

class MainActionReducerImpl @Inject constructor() : MainActionReducer {
    override fun reduce(state: UiState, action: Action) = action.reduce(state)
}

data class UiState(
    val falling: Falling,
    val message: Event<CharSequence?>?
) {
    companion object {
        fun idle() = UiState(Falling(), null)
    }
}