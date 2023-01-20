package com.example.domain

import com.example.shared.di.IoDispatcher
import com.example.shared.interaction.SuspendingUseCase
import com.example.shared.sensor.FallingSensor
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetSensorStateUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher
) : SuspendingUseCase<GetSensorStateUseCase.Params, GetSensorStateUseCase.Result>(dispatcher) {

    override suspend fun execute(params: Params) =
        if(params.fallingSensor.isRunning()) Result.Running
        else Result.Stop

    class Params(val fallingSensor: FallingSensor)

    sealed interface Result {
        object Running: Result
        object Stop: Result
    }
}