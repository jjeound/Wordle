package com.wordle.wordle.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.wordle.wordle.presentation.play.single.singlePlayNavigation
import com.wordle.wordle.presentation.start.startNavigation

@Composable
fun WordleNavHost(
    navController: NavHostController,
    startDestination: WordleBaseRoute,
) {
    NavHost(navController = navController, startDestination = startDestination){
        startNavigation()
        singlePlayNavigation()
    }
}