package com.example.shared.extensions

import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlin.contracts.contract
import kotlin.coroutines.CoroutineContext

suspend fun asyncLaunch(
    coroutineContext: CoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    CoroutineScope(coroutineContext).launch { block() }
}

suspend fun newSupervisorJob(
    dispatcher: CoroutineDispatcher,
    block: suspend CoroutineScope.() -> Job
): Job {
    val job = kotlin.coroutines.coroutineContext[Job]
    return newSupervisorScope(dispatcher).block().also {
        job?.invokeOnCompletion { job.cancel() }
    }
}

suspend fun newSupervisorScope(
    dispatcher: CoroutineDispatcher
) = coroutineScope { (this + SupervisorJob() + dispatcher) }