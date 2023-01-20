package com.example.fallingaccident.ui.main

import com.example.domain.StartSensorUseCase
import com.example.domain.StopSensorUseCase
import com.example.shared.hanlder.AbstractEffectHandler
import com.example.shared.sensor.FallingSensor
import javax.inject.Inject

class MainEffectHandler @Inject constructor(
    dispatcher: MainActionDispatcher,
    private val fallingSensor: FallingSensor,
    private val startUseCase: StartSensorUseCase,
    private val stopUseCase: StopSensorUseCase
) : AbstractEffectHandler<Intention.Effect>(),
    MainActionDispatcher by dispatcher {

    override suspend fun execute(intent: Intention.Effect) {
        when (intent) {
            Intention.Effect.Sensor.Start -> {
                startUseCase(StartSensorUseCase.Params(fallingSensor))
                    .onSuccess { dispatch(Action.Message(getMessage(it))) }
                    .onFailure { dispatch(Action.Message("Start Failure")) }
            }

            Intention.Effect.Sensor.Stop -> stopUseCase(StopSensorUseCase.Params(fallingSensor))
                .onSuccess { dispatch(Action.Message("Stop")) }
                .onFailure { dispatch(Action.Message("Stop failure")) }
        }
    }

    private fun getMessage(result: StartSensorUseCase.Result) =
        when (result) {
            StartSensorUseCase.Result.Success -> "Start"
            StartSensorUseCase.Result.Running -> "Sensor is running"
        }
}