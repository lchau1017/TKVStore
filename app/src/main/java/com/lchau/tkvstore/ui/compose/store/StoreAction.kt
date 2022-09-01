package com.lchau.tkvstore.ui.compose.store

sealed class StoreAction {
    object SubmitAction : StoreAction()
    data class InputAction(val command: String) : StoreAction()
}