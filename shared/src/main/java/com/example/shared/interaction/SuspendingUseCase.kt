package com.example.shared.interaction

import kotlinx.coroutines.*
import kotlin.Exception

abstract class SuspendingUseCase<in P, out T>(
    private val coroutineDispatcher: CoroutineDispatcher
): UseCase<P, T> where T : Any {

    override suspend operator fun invoke(
        params: P
    ) = try {
        withContext(coroutineDispatcher) {
            execute(params).let {
                Result.success(it)
            }
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
