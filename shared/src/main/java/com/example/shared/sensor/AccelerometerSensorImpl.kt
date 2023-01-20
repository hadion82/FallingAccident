package com.example.shared.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow

class AccelerometerSensorImpl(
    private val context: Context
) : AccelerometerSensor {

    private fun getSensorManager() =
        context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager

    private fun accelerometerSensor(): Sensor? {
        val manager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        return manager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun accelerometerFlow() = callbackFlow {
        val sensor = checkNotNull(accelerometerSensor()) { "Accelerometer sensor is null" }
        val manager = checkNotNull(getSensorManager()) { "Sensor manager is null" }

        SimpleSensorEventListener {
            val accX: Float = it?.values?.get(0) ?: .0f
            val accY: Float = it?.values?.get(1) ?: .0f
            val accZ: Float = it?.values?.get(2) ?: .0f
            trySendBlocking(Acceleration(accX, accY, accZ))
        }.let {
            manager.registerListener(it, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            awaitClose { manager.unregisterListener(it) }
        }
    }
}