package com.wordle.wordle.presentation.play

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.wordle.wordle.R
import com.wordle.wordle.navigation.currentComposeNavigator
import com.wordle.wordle.ui.theme.WordleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayTopbar(){
    val composeNavigator = currentComposeNavigator
    TopAppBar(
        modifier = Modifier.padding(horizontal = 12.dp),
        title = {
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    composeNavigator.navigateUp()
                }
            ) {
                Icon(
                    tint = Color.Unspecified,
                    imageVector = ImageVector.vectorResource(R.drawable.chevron_left),
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