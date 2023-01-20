package com.example.domain

import com.example.shared.di.IoDispatcher
import com.example.shared.interaction.SuspendingUseCase
import com.example.shared.sensor.FallingSensor
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class StopSensorUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher
) : SuspendingUseCase<StopSensorUseCase.Params, Unit>(dispatcher) {

    override suspend fun execute(params: Params) {
        params.fallingSensor.stop()
    }

    class Params(val fallingSensor: FallingSensor)
}