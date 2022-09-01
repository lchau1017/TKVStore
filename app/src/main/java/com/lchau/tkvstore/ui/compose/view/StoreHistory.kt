package com.lchau.tkvstore.ui.compose.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lchau.tkvstore.ui.compose.theme.BIG_MARGIN
import com.lchau.tkvstore.ui.compose.theme.SMALL_MARGIN

@Composable
fun OutputView(historyHtml: String, modifier: Modifier) {
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        HtmlText(
            html = historyHtml,
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(
                    top = BIG_MARGIN.dp,
                    bottom = BIG_MARGIN.dp,
                    start = SMALL_MARGIN.dp,
                    end = SMALL_MARGIN.dp
                )
        )
    }
    LaunchedEffect(scrollState.maxValue) {
        scrollState.scrollTo(scrollState.maxValue)
    }
}