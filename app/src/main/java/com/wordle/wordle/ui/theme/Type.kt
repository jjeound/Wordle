package com.wordle.wordle.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.wordle.wordle.R

@Immutable
data class CustomTypography(
    val button: TextStyle,
    val buttonSmall: TextStyle,
    val placeholder: TextStyle,
    val timer: TextStyle,
    val word: TextStyle,
    val title: TextStyle,
    val result: TextStyle,
    val instruction: TextStyle,
    val name: TextStyle,
)

internal val fontFamily = FontFamily(
    Font(R.font.comfortaa_bold, FontWeight.Bold),
    Font(R.font.comfortaa_semibold, FontWeight.SemiBold),
    Font(R.font.comfortaa_medium, FontWeight.Medium),
    Font(R.font.comfortaa_regular, FontWeight.Normal),
    Font(R.font.comfortaa_light, FontWeight.Light),
)

internal val customTypography =  CustomTypography(
    button = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = 18.sp),
    buttonSmall = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = 12.sp),
    placeholder = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Light, fontSize = 12.sp),
    timer = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = 18.sp),
    word = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = 18.sp),
    title = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Bold, fontSize = 48.sp),
    result = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = 18.sp),
    instruction = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = 18.sp),
    name = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = 18.sp)
)