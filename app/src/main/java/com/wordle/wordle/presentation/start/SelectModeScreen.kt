package com.wordle.wordle.presentation.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wordle.wordle.navigation.WordleRoute
import com.wordle.wordle.navigation.currentComposeNavigator
import com.wordle.wordle.ui.theme.WordlePreviewTheme
import com.wordle.wordle.ui.theme.WordleTheme

@Composable
fun SelectModeScreen() {
    val composeNavigator = currentComposeNavigator
    Column(
        modifier = Modifier.fillMaxSize().padding(
            horizontal = 40.dp
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Spacer(
            modifier = Modifier.height(190.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WordleButton(
                text = "5 단어",
                buttonColor = WordleTheme.colors.green,
                onClick = {composeNavigator.navigate(WordleRoute.SinglePlay(5))}
            )
            WordleButton(
                text = "6 단어",
                buttonColor = WordleTheme.colors.yellow,
                onClick = {composeNavigator.navigate(WordleRoute.SinglePlay(6))}
            )
            WordleButton(
                text = "7 단어",
                buttonColor = WordleTheme.colors.orange,
                onClick = {composeNavigator.navigate(WordleRoute.SinglePlay(7))}
            )
        }
    }
}

@Preview
@Composable
fun SelectModeScreenPreview() {
    WordlePreviewTheme {
        SelectModeScreen()
    }
}