package com.wordle.wordle.presentation.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wordle.wordle.R
import com.wordle.wordle.navigation.WordleRoute
import com.wordle.wordle.navigation.currentComposeNavigator
import com.wordle.wordle.ui.theme.WordlePreviewTheme
import com.wordle.wordle.ui.theme.WordleTheme

@Composable
fun StartScreen(
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StartTopbar()
        StartContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartTopbar(){
    val composeNavigator = currentComposeNavigator
    TopAppBar(
        modifier = Modifier.padding(horizontal = 24.dp),
        title = {
        },
        actions = {
            IconButton(
                onClick = {
                    composeNavigator.navigate(WordleRoute.Info)
                }
            ) {
                Icon(
                    tint = Color.Unspecified,
                    imageVector = ImageVector.vectorResource(R.drawable.setting),
                    contentDescription = "info"
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = WordleTheme.colors.bg,
            scrolledContainerColor = WordleTheme.colors.bg,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        )
    )
}

@Composable
fun StartContent(){
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
                text = "싱글 플레이",
                buttonColor = WordleTheme.colors.green,
                onClick = {composeNavigator.navigate(WordleRoute.SelectMode)}
            )
            WordleButton(
                text = "멀티 플레이",
                buttonColor = WordleTheme.colors.yellow,
                onClick = {composeNavigator.navigate(WordleRoute.WaitingRoom)}
            )
        }
    }
}


@Composable
fun Logo(){
    Row {
        Text(
            text = "WO",
            style = WordleTheme.typography.title,
            color = WordleTheme.colors.green,
        )
        Text(
            text = "RD",
            style = WordleTheme.typography.title,
            color = WordleTheme.colors.yellow,
        )
        Text(
            text = "LE",
            style = WordleTheme.typography.title,
            color = WordleTheme.colors.orange,
        )
    }
}

@Composable
fun WordleButton(
    text: String,
    buttonColor: Color,
    onClick: () -> Unit
){
    Button(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = WordleTheme.colors.buttonText,
        ),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = WordleTheme.typography.button
        )
    }
}

@Preview
@Composable
fun StartScreenPreview(){
    WordlePreviewTheme {
        StartScreen()
    }
}