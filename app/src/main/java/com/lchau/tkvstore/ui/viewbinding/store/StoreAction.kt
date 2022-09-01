package com.lchau.tkvstore.ui.viewbinding.store

sealed class StoreAction {
    data class SubmitAction(val command: String) : StoreAction()
}