package com.example.domain

import com.example.shared.di.IoDispatcher
import com.example.shared.interaction.UseCase
import com.example.shared.sensor.FallingSensor
import kotlinx.coroutines.*
import javax.inject.Inject

class StartSensorUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<StartSensorUseCase.Params, StartSensorUseCase.Result> {

    override suspend fun execute(params: Params): Result {
        if (params.sensor.isRunning()) return Result.Running
        params.sensor.start(dispatcher)
        return Result.Success
    }

    class Params(val sensor: FallingSensor)

    sealed interface Result {
        object Success : Result
        object Running : Result
    }
}