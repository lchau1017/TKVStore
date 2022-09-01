package com.lchau.tkvstore.ui.viewbinding.store

sealed class StoreIntent {
    data class SubmitIntent(val command: String) : StoreIntent()
}