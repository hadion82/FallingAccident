package com.example.fallingaccident.ui.main

import com.example.shared.dispatcher.ActionDispatcher
import com.example.shared.sensor.FallingSensor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject
import javax.inject.Singleton

interface MainActionDispatcher : ActionDispatcher<Action>

@Singleton
class MainActionDispatcherImpl @Inject constructor(
    fallingSensor: FallingSensor
) : MainActionDispatcher,
    FallingSensor by fallingSensor {
    override val actions: MutableSharedFlow<Action> = MutableSharedFlow()

    override fun actions(): Flow<Action> =
        merge(actions, results().map { Action.Update(it) })
}