package com.ldlywt.note.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingComponent(visible: Boolean) {
    val interceptClicks = remember { mutableStateOf(visible) }

    DisposableEffect(interceptClicks.value) {
        onDispose {
            interceptClicks.value = false
        }
    }

    val modifier = if (visible) {
        Modifier
            .fillMaxSize()
//            .background(Color.Gray.copy(alpha = 0.3f))
            .clickable(enabled = interceptClicks.value) { /* Do nothing when clicked */ }
    } else {
        Modifier
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (visible) {
            FadingCircle()
        }
    }
}
