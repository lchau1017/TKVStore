package com.lchau.tkvstore.ui.compose.store

sealed class StoreIntent {
    object SubmitIntent : StoreIntent()
    data class InputIntent(val command: String) : StoreIntent()
}