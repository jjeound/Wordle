package com.wordle.wordle.presentation.start

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wordle.wordle.navigation.WordleBaseRoute
import com.wordle.wordle.navigation.WordleRoute

fun NavGraphBuilder.startNavigation(

) {
    navigation<WordleBaseRoute.StartBase>(startDestination = WordleRoute.Start) {
        composable<WordleRoute.Start>{
            StartScreen()
        }
        composable<WordleRoute.SelectMode>{
            SelectModeScreen()
        }
    }
}