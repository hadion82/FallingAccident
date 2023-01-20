package com.example.fallingaccident.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.example.shared.sensor.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SensorModule {

    @Singleton
    @Provides
    fun provideRotationSensor(
        @ApplicationContext context: Context
    ): RotationSensor =
        RotationSensorImpl(context)

    @Singleton
    @Provides
    fun provideAccelerometerSensor(
        @ApplicationContext context: Context
    ): AccelerometerSensor =
        AccelerometerSensorImpl(context)

    @Singleton
    @Provides
    fun provideFallingSensor(
        rotationSensor: RotationSensor,
        accelerometerSensor: AccelerometerSensor
    ): FallingSensor =
        FallingSensorImpl(rotationSensor, accelerometerSensor)
}