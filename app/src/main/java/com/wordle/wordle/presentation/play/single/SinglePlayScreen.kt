package com.wordle.wordle.presentation.play.single

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wordle.wordle.presentation.play.PlayTopbar
import com.wordle.wordle.ui.theme.WordleTheme
import kotlinx.coroutines.delay

@Composable
fun SinglePlayScreen(
    viewModel: SinglePlayViewModel = hiltViewModel(),
    wordsCount: Int
) {
    viewModel.words.collectAsStateWithLifecycle()
    val word by viewModel.word.collectAsStateWithLifecycle()
    val attempt by viewModel.attempt.collectAsStateWithLifecycle()
    val resultState by viewModel.resultState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val userInput by viewModel.userInput.collectAsStateWithLifecycle()
   // val timer by viewModel.timer.collectAsStateWithLifecycle()
    val isShowResult by viewModel.isShowResult.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.eventFlow.collect { event ->
            when(event){
                is SinglePlayEvent.NoWordsLeft -> {
                }
                is SinglePlayEvent.NoAttemptsLeft -> {
                }
                is SinglePlayEvent.CorrectAnswer -> {
                }
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PlayTopbar()
        if(uiState == SinglePlayUiState.Idle && word != null){
            SinglePlayContent(
                word = word!!,
                wordsCount = wordsCount,
                resultState = resultState,
                userInput = userInput,
                onValueChange = viewModel::onValueChange,
                attempt = attempt,
                onSubmit = {
                    viewModel.onSubmit(userInput[attempt].joinToString(""))
                },
//                timer = timer,
//                reduceTimer = viewModel::reduceTimer,
                isShowResult = isShowResult,
                getNewWord = viewModel::getNewWord
            )
        } else if(uiState == SinglePlayUiState.Loading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = WordleTheme.colors.icon
                )
            }
        }
    }
}

@Composable
fun SinglePlayContent(
    word: String,
    wordsCount: Int,
    resultState: List<AnswerResult>,
    userInput: List<List<String>>,
    onValueChange: (Int, String) -> Unit,
    attempt: Int,
    onSubmit: () -> Unit,
//    timer: Int,
//    reduceTimer: () -> Unit,
    isShowResult: Boolean,
    getNewWord: () -> Unit
) {
    val primaryColor = when(wordsCount){
        5 -> WordleTheme.colors.green
        6 -> WordleTheme.colors.yellow
        7 -> WordleTheme.colors.orange
        else -> WordleTheme.colors.green
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 30.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
//            Timer(
//                timer = timer,
//                reduceTimer = reduceTimer,
//                onFinish = onSubmit
//            )
//            Spacer(
//                modifier = Modifier.height(30.dp)
//            )
            Text(
                text = "The Answer is",
                style = WordleTheme.typography.instruction,
                color = if(isShowResult) WordleTheme.colors.text else WordleTheme.colors.bg
            )
            AnswerBoard(
                word = word,
                boxColor = primaryColor,
                isShowResult = isShowResult,
                getNewWord = getNewWord
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            GuessField(
                input = userInput,
                onValueChange = onValueChange,
                length = wordsCount,
                attempt = attempt,
                resultState = resultState
            )
        }
        SubmitButton(
            buttonColor = primaryColor,
            onSubmit = onSubmit
        )
    }
}

//@Composable
//fun Timer(
//    timer: Int,
//    reduceTimer: () -> Unit,
//    onFinish: () -> Unit
//){
//    LaunchedEffect(timer) {
//        if (timer > 0) {
//            delay(1000)
//            reduceTimer()
//        }
//        if (timer == 0) {
//            onFinish()
//        }
//    }
//    Text(
//        text = "$timer",
//        style = WordleTheme.typography.timer,
//        color = WordleTheme.colors.text
//    )
//}

@Composable
fun AnswerBoard(
    word: String,
    boxColor: Color,
    isShowResult: Boolean,
    getNewWord: () -> Unit
) {
    val flipStates = remember { mutableStateListOf<Boolean>().apply { repeat(word.length) { add(true) } } }

    // isShowResult가 true가 되면 순차 애니메이션 실행
    LaunchedEffect(isShowResult) {
        if (isShowResult) {
            for (i in word.indices) {
                flipStates[i] = false
                delay(300) // 한 글자씩 0.3초 간격
            }
            delay(500) // 모든 글자 뒤집힌 후 잠시 대기
            getNewWord() // 모든 글자 뒤집힌 후 콜백 실행
            flipStates.fill(true) // 모든 글자 뒤집힌 상태 유지
        }
    }
    Column {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            word.forEachIndexed { index, letter ->
                val isFlipped = flipStates.getOrNull(index) ?: false
                val rotation = animateFloatAsState(
                    targetValue = if (isFlipped) 180f else 0f,
                    animationSpec = tween(durationMillis = 300),
                    label = ""
                )

                Card(
                    modifier = Modifier
                        .size(40.dp)
                        .graphicsLayer(
                            rotationY = rotation.value,
                            cameraDistance = 8 * 12.5f
                        ),
                    shape = RoundedCornerShape(6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = boxColor,
                        contentColor = if (rotation.value >= 90f) boxColor else WordleTheme.colors.buttonText
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = letter.toString(),
                            style = WordleTheme.typography.button
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GuessField(
    input: List<List<String>>,
    onValueChange: (Int, String) -> Unit,
    length: Int,
    attempt: Int,
    resultState: List<AnswerResult>
) {
    val focusRequesters = List(length) { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val allowedRegex = Regex("[A-Za-z]")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth().padding(
            horizontal = 10.dp
        )
    ) {
        repeat(length) { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(length) { col ->
                    val isEnabled = row == attempt
                    val currentText = input[row][col]
                    Box(
                        modifier = Modifier
                            .background(
                                color = when{
                                    resultState.getOrNull(row)?.result?.getOrNull(col) == AnswerState.Correct -> WordleTheme.colors.green
                                    resultState.getOrNull(row)?.result?.getOrNull(col) == AnswerState.Present -> WordleTheme.colors.yellow
                                    resultState.getOrNull(row)?.result?.getOrNull(col) == AnswerState.Incorrect -> WordleTheme.colors.red
                                    else -> WordleTheme.colors.bg
                                },
                                shape = RoundedCornerShape(6.dp)
                            )
                            .size(40.dp)
                            .border(
                                width = if(attempt < row) 0.dp else 1.dp,
                                color = if(attempt > row) Color.Transparent else WordleTheme.colors.stroke,
                                shape = RoundedCornerShape(6.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ){
                        BasicTextField(
                            value = currentText,
                            onValueChange = { new ->
                                if (allowedRegex.matches(new)) {
                                    onValueChange(col, new.uppercase())
                                    // 다음 칸 이동
                                    if (col < length - 1) {
                                        focusRequesters[col + 1].requestFocus()
                                    }
                                } else if (new.isBlank()){
                                    onValueChange(col, "")
                                }
                            },
                            singleLine = true,
                            enabled = isEnabled,
                            modifier = Modifier
                                .matchParentSize()
                                .alpha(0f)
                                .focusRequester(focusRequesters[col])
                                .onKeyEvent { event ->
                                    if (event.key == Key.Backspace) {
                                        if (col > 0) {
                                            focusRequesters[col - 1].requestFocus()
                                        }
                                    }
                                    false
                                },
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            ),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Characters,
                                keyboardType = KeyboardType.Ascii
                            )
                        )
                        Text(
                            text = currentText,
                            style = WordleTheme.typography.word,
                            color = if(attempt > row) WordleTheme.colors.buttonText else WordleTheme.colors.text
                        )
                    }
                }
            }
        }
    }

    // 처음 시작 시 자동 포커스
    LaunchedEffect(attempt) {
        focusRequesters.first().requestFocus()
    }
}


@Composable
fun SubmitButton(
    buttonColor: Color,
    onSubmit: () -> Unit,
){
    Box(
        modifier = Modifier.padding(vertical = 8.dp)
    ){
        Button(
            modifier = Modifier.fillMaxWidth().padding(vertical = 11.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = WordleTheme.colors.buttonText,
            ),
            onClick = onSubmit
        ) {
            Text(
                text = "Submit",
                style = WordleTheme.typography.button
            )
        }
    }
}


@Preview
@Composable
fun SinglePlayContentPreview(){
    WordleTheme {
        SinglePlayContent(
            word = "APPLE",
            wordsCount = 5,
            userInput = listOf(
                listOf("H", "E", "L", "L", "O"),
                listOf("B", "E", "S", "C", "O"),
                listOf("", "", "", "", ""),
                listOf("", "", "", "", ""),
                listOf("", "", "", "", "")
            ),
            onValueChange = {_, _ -> },
            attempt = 1,
            onSubmit = {},
            resultState = listOf(
                AnswerResult(
                    guess = "HELLO",
                    result = listOf(
                        AnswerState.Incorrect,
                        AnswerState.Incorrect,
                        AnswerState.Incorrect,
                        AnswerState.Correct,
                        AnswerState.Incorrect,
                    )
                )
            ),
            isShowResult = false,
            getNewWord = {}
        )
    }
}