package com.example.fallingaccident.ui.main

import com.example.domain.GetSensorStateUseCase
import com.example.shared.hanlder.AbstractActionHandler
import com.example.shared.sensor.FallingSensor
import javax.inject.Inject

class MainActionHandler @Inject constructor(
    dispatcher: MainActionDispatcher,
    private val fallingSensor: FallingSensor,
    private val checkSensorUseCase: GetSensorStateUseCase
) : AbstractActionHandler<Intention.Action>(),
    MainActionDispatcher by dispatcher {

    override suspend fun execute(intent: Intention.Action) {
        when (intent) {
            is Intention.Action.Message ->
                checkSensorUseCase(GetSensorStateUseCase.Params(fallingSensor))
                    .onSuccess { result -> dispatch(Action.Message(getMessage(result))) }
        }
    }

    private fun getMessage(result: GetSensorStateUseCase.Result) =
        when (result) {
            GetSensorStateUseCase.Result.Running -> "Sensor is running"
            GetSensorStateUseCase.Result.Stop -> "Sensor is stop"
        }
}