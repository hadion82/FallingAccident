package com.example.shared.sensor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface FallingSensor {
    suspend fun start(dispatcher: CoroutineDispatcher)
    suspend fun stop()
    fun results(): Flow<Falling>

    fun isRunning(): Boolean
}

data class Falling(
    val rX: Float = .0f,
    val rY: Float = .0f,
    val rZ: Float = .0f,
    val aX: Float = .0f,
    val aY: Float = .0f,
    val aZ: Float = .0f
)