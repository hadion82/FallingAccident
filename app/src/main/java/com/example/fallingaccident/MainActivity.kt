package com.example.fallingaccident

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import com.example.fallingaccident.ui.main.*
import com.example.fallingaccident.ui.theme.CenterColumn
import com.example.fallingaccident.ui.theme.DefaultSurface
import com.example.fallingaccident.ui.theme.FallingAccidentTheme
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity(), MainActivityScope {

    override val intents: MutableSharedFlow<Intention> =
        MutableSharedFlow()

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        val viewState = viewModel.viewState
        setContent {
            MainScreen(viewState = viewState)
        }

        intents.onEach(viewModel::processIntent)
            .launchIn(lifecycleScope)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FallingAccidentTheme {
        DefaultSurface {
            val dispatcher = MainScopedDispatcher(MutableSharedFlow(), MainScope())
            CenterColumn {
                dispatcher.StartButton(Intention.Effect.Sensor.Start)
                dispatcher.StopButton(Intention.Effect.Sensor.Stop)
            }
        }
    }
}