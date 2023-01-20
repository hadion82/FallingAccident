package com.example.fallingaccident.ui.scope

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.example.shared.dispatcher.ScopedDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow

interface UiScope<I, D : ScopedDispatcher<I>> {
    val intents: MutableSharedFlow<I>
}

inline fun <I, reified D : ScopedDispatcher<I>> UiScope<I, D>.rememberProducer(scope: CoroutineScope) =
    D::class.java.constructors.first().newInstance(intents, scope) as D

@Composable
inline fun <I, reified D : ScopedDispatcher<I>> UiScope<I, D>.ComposeScope(content: @Composable D.() -> Unit) =
    with(rememberProducer(rememberCoroutineScope())) {
        content()
    }