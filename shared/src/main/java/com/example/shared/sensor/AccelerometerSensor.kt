package com.example.shared.sensor

import kotlinx.coroutines.flow.Flow

interface AccelerometerSensor {
    fun accelerometerFlow(): Flow<Acceleration>
}

data class Acceleration(val x: Float, val y: Float, val z: Float)