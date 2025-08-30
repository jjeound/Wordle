package com.wordle.wordle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.wordle.wordle.navigation.AppComposeNavigator
import com.wordle.wordle.navigation.WordleBaseRoute
import com.wordle.wordle.navigation.WordleNavHost
import com.wordle.wordle.navigation.WordleRoute

@Composable
fun WordleMain(
    composeNavigator: AppComposeNavigator<WordleRoute>,
    uiState: MainUiState,
    startDestination: WordleBaseRoute,
){
    val navHostController = rememberNavController()
    LaunchedEffect(Unit) {
        composeNavigator.handleNavigationCommands(navHostController)
    }
    WordleNavHost(
        navController = navHostController,
        startDestination = startDestination,
    )
}