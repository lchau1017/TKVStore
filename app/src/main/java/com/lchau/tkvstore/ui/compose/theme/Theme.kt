package com.lchau.tkvstore.ui.compose.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColors(
    surface = DraculaBlack, // top app bar
    primary = DraculaWhite,
    primaryVariant = DraculaGrey,
    onPrimary = DraculaBlack,
    onSecondary = Color.Black,
    background = DraculaBlack,
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = DarkColors,
        content = content
    )
}