package com.example.shared.sensor

import kotlinx.coroutines.flow.Flow

interface RotationSensor {

    companion object {
        const val RADIAN_TO_DEGREE = -180.0f / kotlin.math.PI.toFloat()
    }

    fun rotateFlow(): Flow<Rotation>
}

data class Rotation(val x: Float, val y: Float, val z: Float)