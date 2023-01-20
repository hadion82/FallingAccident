package com.example.shared.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.view.Surface
import android.view.WindowManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow

class RotationSensorImpl(private val context: Context) : RotationSensor {

    private var sensorManager: SensorManager? = null

    private val sensorList = intArrayOf(
        Sensor.TYPE_ROTATION_VECTOR,
        Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR,
        Sensor.TYPE_GAME_ROTATION_VECTOR
    )

    private fun getSensor(): Sensor? {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        sensorManager?.let { manager ->
            sensorList.forEach { type ->
                manager.getDefaultSensor(type)?.let { sensor ->
                    return sensor
                }
            }
        }
        return null
    }

    private fun getWindowManager() =
        context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager

    override fun rotateFlow() = callbackFlow {
        val sensor = checkNotNull(getSensor()) { "Default type sensor is null" }
        val windowManager = checkNotNull(getWindowManager()) { "WindowManager is null" }
        val rotationMatrix = FloatArray(16) { if (it % 4 == 0) 1f else 0f }
        SimpleSensorEventListener {
            val adjustMatrix = FloatArray(16)
            val axis = windowManager.remapRotationAxis()
            SensorManager.getRotationMatrixFromVector(rotationMatrix, it?.values)
            SensorManager.remapCoordinateSystem(
                rotationMatrix,
                axis.first,
                axis.second,
                adjustMatrix
            )

            val orientation = FloatArray(3)
            SensorManager.getOrientation(adjustMatrix, orientation)
            trySendBlocking(
                Rotation(
                    orientation[0].toDegree(),
                    orientation[1].toDegree(),
                    orientation[2].toDegree()
                )
            )

        }.let {
            sensorManager?.registerListener(it, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            awaitClose { sensorManager?.unregisterListener(it) }
        }
    }

    private fun WindowManager.remapRotationAxis() =
        when (defaultDisplay?.rotation) {
            Surface.ROTATION_0 -> SensorManager.AXIS_X to SensorManager.AXIS_Y
            Surface.ROTATION_90 -> SensorManager.AXIS_Y to SensorManager.AXIS_MINUS_X
            Surface.ROTATION_180 -> SensorManager.AXIS_MINUS_X to SensorManager.AXIS_MINUS_Y
            else -> SensorManager.AXIS_MINUS_Y to SensorManager.AXIS_X
        }

    private fun Float.toDegree() = this * RotationSensor.RADIAN_TO_DEGREE
}