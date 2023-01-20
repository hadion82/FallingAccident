package com.example.shared.sensor

import com.example.shared.extensions.asyncLaunch
import com.example.shared.translator.Combine
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.coroutineContext

class FallingSensorImpl(
    rotateSensor: RotationSensor,
    accelerometerSensor: AccelerometerSensor
) : FallingSensor,
    RotationSensor by rotateSensor,
    AccelerometerSensor by accelerometerSensor {

    private val resultFlow = MutableSharedFlow<Falling>()

    private var job: Job? = null

    private var isRunning = false

    private val asFalling = Combine<Rotation, Acceleration, Falling> { rotation, acceleration ->
        Falling(rotation.x, rotation.y, rotation.z, acceleration.x, acceleration.y, acceleration.z)
    }

    override suspend fun start(dispatcher: CoroutineDispatcher) =
        asyncLaunch(coroutineContext + dispatcher) {
            isRunning = true
            try {
                job = combine(rotateFlow(), accelerometerFlow(), asFalling)
                    .onEach { resultFlow.emit(it) }
                    .launchIn(this)
            } catch (e: Exception) {
                isRunning = false
                throw e
            }
        }

    override suspend fun stop() {
        job?.cancel()
        isRunning = false
    }

    override fun results(): Flow<Falling> = resultFlow

    override fun isRunning(): Boolean = isRunning
}