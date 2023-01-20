package com.example.fallingaccident.ui.main

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.fallingaccident.R
import com.example.shared.dispatcher.ScopedDispatcher
import com.example.fallingaccident.ui.scope.ComposeScope
import com.example.fallingaccident.ui.scope.UiScope
import com.example.fallingaccident.ui.theme.CenterColumn
import com.example.fallingaccident.ui.theme.DefaultSurface
import com.example.fallingaccident.ui.theme.FallingAccidentTheme
import com.example.fallingaccident.ui.theme.SimpleButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainScopedDispatcher(
    intent: MutableSharedFlow<Intention>,
    scope: CoroutineScope
) :
    ScopedDispatcher<Intention>(intent, scope)

interface MainActivityScope : UiScope<Intention, MainScopedDispatcher>

@Composable
fun MainScopedDispatcher.StartButton(
    effect: Intention
) =
    SimpleButton(
        onClick = { dispatch(effect) },
        text = "Start",
        bottom = 10.0f
    )

@Composable
fun MainScopedDispatcher.StopButton(
    effect: Intention
) =
    SimpleButton(
        onClick = { dispatch(effect) },
        text = "Stop",
        bottom = 10.0f
    )

@Composable
fun MainScopedDispatcher.StateButton() =
    SimpleButton(
        onClick = { dispatch(Intention.Action.Message.State) },
        text = "State",
        bottom = 10.0f
    )

@Composable
fun MainActivityScope.FallingCoefficientText(
    viewState: Flow<UiState>
) {
    val state = viewState.collectAsState(initial = UiState.idle())
    Text(
        modifier = Modifier.padding(40.dp, 0.dp),
        text = state.value.falling.toString()
    )
}

@Composable
fun MainActivityScope.ToastMessage(
    viewState: Flow<UiState>
) {
    val context = LocalContext.current
    LaunchedEffect(this) {
        viewState
            .onEach {
                it.message?.getContentIfNotHandled()?.let { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
            .launchIn(this)
    }
}

@Composable
fun MainActivityScope.MainScreen(viewState: Flow<UiState>) {
    FallingAccidentTheme {
        DefaultSurface {
            ComposeScope {
                CenterColumn {
                    StartButton(Intention.Effect.Sensor.Start)
                    StopButton(Intention.Effect.Sensor.Stop)
                    StateButton()
                    FallingCoefficientText(viewState)
                    ToastMessage(viewState)
                }
            }
        }
    }
}