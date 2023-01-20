package com.example.shared.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

class SimpleSensorEventListener(private val onChanged: (event: SensorEvent?) -> Unit) :
    SensorEventListener {
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) =
        onChanged(event)
}