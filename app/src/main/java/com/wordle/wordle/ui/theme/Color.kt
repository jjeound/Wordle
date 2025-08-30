package com.wordle.wordle.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.wordle.wordle.R

internal val White = Color(0xFFFFFFFF)
internal val Black = Color(0xFF000000)
internal val Green = Color(0xFF66D47E)
internal val Orange = Color(0xFFF4AF1B)
internal val Yellow = Color(0xFFFFD55A)
internal val Grey100 = Color(0xFFDCDCDC)
internal val Grey200 = Color(0xFFB2B2B2)
internal val Red = Color(0xFFFFA2A2)

@Immutable
data class CustomColors(
    val icon: Color,
    val green: Color,
    val orange: Color,
    val yellow: Color,
    val stroke: Color,
    val loading: Color,
    val text: Color,
    val buttonText: Color,
    val placeholder: Color,
    val red: Color,
    val bg: Color,
)

val LightCustomColors = CustomColors(
    icon = Grey200,
    green = Green,
    orange = Orange,
    yellow = Yellow,
    stroke = Black,
    loading = Grey100,
    text = Black,
    buttonText = White,
    placeholder = Grey200,
    red = Red,
    bg = White
)
val DarkCustomColors = CustomColors(
    icon = Grey200,
    green = Green,
    orange = Orange,
    yellow = Yellow,
    stroke = Black,
    loading = Grey100,
    text = White,
    buttonText = White,
    placeholder = Grey200,
    red = Red,
    bg = Black
)