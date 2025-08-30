package com.wordle.wordle.presentation.play.single

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.wordle.wordle.navigation.WordleRoute

fun NavGraphBuilder.singlePlayNavigation(
) {
    composable<WordleRoute.SinglePlay>{
        val args = it.toRoute<WordleRoute.SinglePlay>()
        SinglePlayScreen(
            wordsCount = args.wordsCount
        )
    }
}