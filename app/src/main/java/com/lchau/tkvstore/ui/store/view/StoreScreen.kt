package com.lchau.tkvstore.ui.store.view

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.lchau.tkvstore.ui.extension.collectAsStateLifecycleAware
import com.lchau.tkvstore.ui.store.StoreState
import com.lchau.tkvstore.ui.store.StoreViewModel

@Composable
internal fun StoreScreen(
    viewModel: StoreViewModel = hiltViewModel()
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

