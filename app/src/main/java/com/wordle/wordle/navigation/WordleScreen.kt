package com.wordle.wordle.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface WordleRoute{
    @Serializable data object SignIn: WordleRoute
    @Serializable data object SignUp: WordleRoute
    @Serializable data object Start: WordleRoute
    @Serializable data object SelectMode: WordleRoute
    @Serializable data class SinglePlay(val wordsCount: Int): WordleRoute
    @Serializable data object MultiPlay: WordleRoute
    @Serializable data object WaitingRoom: WordleRoute
    @Serializable data object Info: WordleRoute
}

@Serializable
sealed interface WordleBaseRoute{
    @Serializable data object StartBase: WordleBaseRoute
}
