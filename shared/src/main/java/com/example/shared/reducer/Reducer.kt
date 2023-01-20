package com.example.shared.reducer

interface ActionReducer<S, in A> {
    fun reduce(state: S, action: A): S
}