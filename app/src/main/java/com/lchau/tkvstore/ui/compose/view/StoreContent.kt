package com.lchau.tkvstore.ui.compose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.lchau.tkvstore.ui.compose.store.StoreState
import com.lchau.tkvstore.ui.compose.theme.DraculaWhite
import com.lchau.tkvstore.ui.compose.theme.SMALL_MARGIN

@Composable
fun StoreContent(
    state: StoreState,
    onTextChanged: (String) -> Unit,
    onInputSubmitted: () -> Unit
) {
    val inputBarMargin = SMALL_MARGIN.dp
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val (output, inputText, inputBar) = createRefs()
        val barrier = createTopBarrier(inputText, inputBar)
        Box(
            Modifier
                .constrainAs(inputBar) {
                    top.linkTo(barrier)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .defaultMinSize(minWidth = Dp.Unspecified, minHeight = 56.dp)
                .background(DraculaWhite)
                .fillMaxWidth()
        )

        InputText(
            state.inputTextToDisplay,
            Modifier.constrainAs(inputText) {
                top.linkTo(barrier, inputBarMargin)
                bottom.linkTo(parent.bottom, inputBarMargin)
                start.linkTo(parent.start, inputBarMargin)
                end.linkTo(parent.end, inputBarMargin)
                width = Dimension.fillToConstraints
            },
            onTextChanged,
            onInputSubmitted
        )

        OutputView(
            state.historyToDisplay,
            Modifier.constrainAs(output) {
                top.linkTo(parent.top)
                bottom.linkTo(inputBar.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        )
    }
}