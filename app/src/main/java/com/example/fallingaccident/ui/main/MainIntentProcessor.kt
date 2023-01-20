package com.example.fallingaccident.ui.main

import com.example.shared.hanlder.ActionHandler
import com.example.shared.hanlder.EffectHandler
import com.example.shared.processor.IntentProcessor
import javax.inject.Inject

interface MainProcessor : IntentProcessor<Intention, Action>

class MainIntentProcessor @Inject constructor(
    actionHandler: MainActionHandler,
    effectHandler: MainEffectHandler
) : MainProcessor,
    ActionHandler<Intention.Action> by actionHandler,
    EffectHandler<Intention.Effect> by effectHandler {

    override suspend fun process(intent: Intention) {
        when (intent) {
            is Intention.Action -> handleAction(intent)
            is Intention.Effect -> handleEffect(intent)
        }
    }
}