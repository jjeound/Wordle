package com.wordle.wordle.presentation.play.single

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wordle.wordle.data.repository.WordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SinglePlayViewModel @Inject constructor(
    wordsRepository: WordsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val uiState: MutableStateFlow<SinglePlayUiState> = MutableStateFlow(SinglePlayUiState.Loading)
    val wordsCount = savedStateHandle.getStateFlow<Int?>("wordsCount", null)
    val counts = savedStateHandle.get<Int>("wordsCount") ?: 5

    @OptIn(ExperimentalCoroutinesApi::class)
    val words: StateFlow<List<String>> = wordsCount.filterNotNull().flatMapLatest { count ->
        wordsRepository.getWordsByLength(count)
    }.onEach {
        getNewWord()
    }.catch {
        uiState.value = SinglePlayUiState.Error(it.message ?: "Unknown Error")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    private val _word = MutableStateFlow<String?>(null) // 현재 문제
    val word: StateFlow<String?> = _word.asStateFlow()

    private val _attempt = MutableStateFlow(0) // 현재 시도 횟수
    val attempt: StateFlow<Int> = _attempt.asStateFlow()

    private var stage = mutableIntStateOf(0) // 현재 문제 번호

    private val _timer = MutableStateFlow(30)
    val timer: StateFlow<Int> = _timer.asStateFlow()

    private val _userInput = MutableStateFlow(
        List(counts) { MutableList(counts) { "" } }
    )
    val userInput: StateFlow<List<MutableList<String>>> = _userInput.asStateFlow()

    private val _resultState = MutableStateFlow<List<AnswerResult>>(emptyList())
    val resultState: StateFlow<List<AnswerResult>> = _resultState.asStateFlow()

    private val _isShowResult = MutableStateFlow<Boolean>(false)
    val isShowResult: StateFlow<Boolean> = _isShowResult.asStateFlow()

    private val _eventFlow: MutableSharedFlow<SinglePlayEvent> = MutableSharedFlow()
    val eventFlow: SharedFlow<SinglePlayEvent> = _eventFlow.asSharedFlow()

    fun getNewWord(){
        viewModelScope.launch {
            if (words.value.isNotEmpty() && stage.intValue < words.value.size) {
                _word.value = words.value[stage.intValue++]
                _attempt.value = 0
                _resultState.value = emptyList()
                _userInput.value = List(counts) { MutableList(counts) { "" } }
                _timer.value = 30
                _isShowResult.value = false
            } else {
                _eventFlow.emit(SinglePlayEvent.NoWordsLeft)
            }
            uiState.value = SinglePlayUiState.Idle
        }
    }

    fun onValueChange(colIdx: Int, value: String) {
        _userInput.update { grid ->
            grid.mapIndexed { rowIdx, rowList ->
                if (rowIdx != _attempt.value) rowList
                else rowList.toMutableList().also { it[colIdx] = value }
            }
        }
    }

    fun onSubmit(guess: String){
        word.value?.let { currentWord ->
            val result = MutableList(guess.length) { AnswerState.Incorrect }
            val answerChars = currentWord.toMutableList()
            for (i in guess.indices) {
                if (guess[i] == currentWord[i]) {
                    result[i] = AnswerState.Correct
                    answerChars[i] = '_' // 소진된 글자는 마킹
                }
            }
            for (i in guess.indices) {
                if (result[i] == AnswerState.Correct) continue // 이미 처리된 건 스킵
                val idx = answerChars.indexOf(guess[i])
                if (idx != -1) {
                    result[i] = AnswerState.Present
                    answerChars[idx] = '_' // 소진
                }
            }
            _resultState.value = _resultState.value + AnswerResult(guess, result)
        }
        checkAnswer()
    }

    fun checkAnswer(){
        viewModelScope.launch {
            if(_resultState.value.lastOrNull()?.result?.all { it == AnswerState.Correct } == true){
                _eventFlow.emit(SinglePlayEvent.CorrectAnswer)
                _isShowResult.value = true
            } else if(_attempt.value + 1 == wordsCount.value!!){
                _eventFlow.emit(SinglePlayEvent.NoAttemptsLeft)
                _isShowResult.value = true
            } else {
                _attempt.value++
                _timer.value = 30
            }
        }
    }

    fun reduceTimer(){
        if(_timer.value > 0){
            _timer.value = _timer.value - 1
        }
    }
}

sealed interface SinglePlayUiState {
    data object Idle: SinglePlayUiState
    data object Loading: SinglePlayUiState
    data class Error(val message: String): SinglePlayUiState
}

sealed interface SinglePlayEvent {
    data object NoWordsLeft: SinglePlayEvent
    data object NoAttemptsLeft: SinglePlayEvent
    data object CorrectAnswer: SinglePlayEvent
}

enum class AnswerState{
    Correct,
    Present,
    Incorrect
}

data class AnswerResult(
    val guess: String,
    val result: List<AnswerState>
)