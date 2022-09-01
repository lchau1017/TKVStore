package com.lchau.tkvstore.ui.viewbinding.store.view

data class UiState(
    val history: List<HistoryView> = emptyList()
)

data class HistoryView(val content: String)

fun List<HistoryView>.append(historyItem: HistoryView): List<HistoryView> =
    this.toMutableList().apply {
        add(historyItem)
    }.toList()