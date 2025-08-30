package com.wordle.wordle.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.wordle.wordle.navigation.LocalComposeNavigator
import com.wordle.wordle.navigation.WordleComposeNavigator

@Composable
fun WordlePreviewTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalComposeNavigator provides WordleComposeNavigator(),
    ) {
        WordleTheme {
            content()
        }
    }
}