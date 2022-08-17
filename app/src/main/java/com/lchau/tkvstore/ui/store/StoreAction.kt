package com.lchau.tkvstore.ui.store

sealed class StoreAction {
    object SubmitAction : StoreAction()
    data class InputAction(val command: String) : StoreAction()
}