package com.example.shared.hanlder

interface ActionHandler<in A> {
    suspend fun handleAction(intent: A)
}

abstract class AbstractActionHandler<in A> : ActionHandler<A> {

    override suspend fun handleAction(intent: A) = execute(intent)

    protected abstract suspend fun execute(intent: A)
}