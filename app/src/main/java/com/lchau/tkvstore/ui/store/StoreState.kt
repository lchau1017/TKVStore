package com.lchau.tkvstore.ui.store

private const val COLOR_CYAN = "#8be9fd"
private const val COLOR_RED = "#ff5555"
private const val COLOR_GREEN = "#23FF90"

data class StoreState(
    val inputTextToDisplay: String = "",
    val history: List<HistoryView> = emptyList()
) {
    val historyToDisplay: String by lazy {
        history.joinToString(
            separator = "<br>",
        )
    }

}

sealed class HistoryView(val text: String) {
    data class InputHistory(val inputText: String) : HistoryView(inputText) {
        override fun toString(): String = "<font color='$COLOR_CYAN'>></font> $text"
    }

    data class OutputHistory(val outputText: String, val isError: Boolean = true) :
        HistoryView(outputText) {
        override fun toString(): String {
            val color = if (isError) COLOR_RED else COLOR_GREEN
            return "<font color='$color'>$text</font>"
        }
    }
}

fun List<HistoryView>.append(historyItem: HistoryView): List<HistoryView> =
    this.toMutableList().apply {
        add(historyItem)
    }.toList()
