package com.wordle.wordle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wordle.wordle.navigation.AppComposeNavigator
import com.wordle.wordle.navigation.LocalComposeNavigator
import com.wordle.wordle.navigation.WordleRoute
import com.wordle.wordle.ui.theme.WordleTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    @Inject
    internal lateinit var composeNavigator: AppComposeNavigator<WordleRoute>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            CompositionLocalProvider(
                LocalComposeNavigator provides composeNavigator,
            ) {
                WordleTheme {
                    WordleMain(
                        composeNavigator = composeNavigator,
                        uiState = uiState,
                        startDestination = startDestination,
                    )
                }
            }
        }
    }
}