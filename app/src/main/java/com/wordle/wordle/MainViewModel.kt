package com.wordle.wordle

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.wordle.wordle.navigation.WordleBaseRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) :ViewModel() {
    val uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState.Idle)

    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = MutableStateFlow<WordleBaseRoute>(WordleBaseRoute.StartBase)
    val startDestination = _startDestination.asStateFlow()

    fun setStartDestination(route: WordleBaseRoute) {
        _startDestination.value = route
    }
}

@Stable
sealed interface MainUiState{
    data object Idle: MainUiState
    data object Loading: MainUiState
    data class Error(val message: String): MainUiState
}
