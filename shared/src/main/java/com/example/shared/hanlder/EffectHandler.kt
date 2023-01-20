package com.example.shared.hanlder

import com.example.shared.extensions.asyncLaunch
import kotlinx.coroutines.currentCoroutineContext

interface EffectHandler<in E> {
    suspend fun handleEffect(intent: E)
}

abstract class AbstractEffectHandler<in E> : EffectHandler<E> {

    override suspend fun handleEffect(intent: E) =
        asyncLaunch(currentCoroutineContext()) {
            execute(intent)
        }

    protected abstract suspend fun execute(intent: E)
}