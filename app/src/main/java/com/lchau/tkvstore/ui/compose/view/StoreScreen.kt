package com.lchau.tkvstore.ui.compose.view

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.lchau.tkvstore.ui.compose.extension.collectAsStateLifecycleAware
import com.lchau.tkvstore.ui.compose.store.StoreState
import com.lchau.tkvstore.ui.compose.store.ComposeStoreViewModel

@Composable
internal fun StoreScreen(
    viewModel: ComposeStoreViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateLifecycleAware(
        initial = StoreState(),
        scope = viewModel.viewModelScope
    ).value

    StoreContent(
        state,
        onTextChanged = viewModel::onTextChanged,
        onInputSubmitted = viewModel::onInputSubmitted
    )
}

