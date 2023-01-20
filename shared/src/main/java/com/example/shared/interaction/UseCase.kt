package com.example.shared.interaction

interface UseCase<in P, out T> where T : Any {

    suspend fun execute(params: P): T

    suspend operator fun invoke(
        params: P
    ) = try {
        execute(params).let {
            Result.success(it)
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
