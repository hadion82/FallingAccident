package com.example.fallingaccident.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DefaultSurface(content: @Composable () -> Unit) = Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colors.background,
    content = content
)