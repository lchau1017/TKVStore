package com.lchau.tkvstore.ui.viewbinding.store.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lchau.tkvstore.domain.usecase.ExecuteCommandUseCase
import com.lchau.tkvstore.ui.viewbinding.store.StoreAction
import com.lchau.tkvstore.ui.viewbinding.store.StoreIntent
import com.lchau.tkvstore.ui.viewbinding.store.view.HistoryView
import com.lchau.tkvstore.ui.viewbinding.store.view.UiState
import com.lchau.tkvstore.ui.viewbinding.store.view.append
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class StoreViewModel constructor(
    private val executeCommandUseCase: ExecuteCommandUseCase,
    initStateState: UiState
) : ViewModel() {

    @Inject
    constructor(
        executeCommandUseCase: ExecuteCommandUseCase
    ) : this(
        executeCommandUseCase,
        UiState(),
    )

    private val _state = MutableStateFlow(initStateState)
    val state: StateFlow<UiState> = _state.asStateFlow()


    fun onSubmit(text: String) {
        dispatchIntent(StoreIntent.SubmitIntent(text))
    }

    private fun intentToAction(intent: StoreIntent): StoreAction {
        return when (intent) {
            is StoreIntent.SubmitIntent -> StoreAction.SubmitAction(intent.command)
        }
    }

    private fun handleAction(storeAction: StoreAction) {
        when (storeAction) {
            is StoreAction.SubmitAction -> {
                val command = storeAction.command
                if (command.isEmpty()) {
                    return
                }
                _state.value = state.value.copy(
                    history = state.value.history.append(
                        HistoryView("<font color='#8be9fd'>></font> $command")
                    )
                )
                executeCommandLineInput(storeAction.command.trim())
            }
        }
    }

    private fun dispatchIntent(intent: StoreIntent) {
        handleAction(intentToAction(intent))
    }

    private fun executeCommandLineInput(input: String) {
        viewModelScope.launch {
            executeCommandUseCase.execute(input)
                .catch {
                    it.message?.let { it1 -> Log.e("executeCommandLineInput error :", it1) }
                }
                .collectLatest { result ->
                    val currentSate = state.value
                    result.onSuccess { response ->
                        response?.let {
                            _state.value = state.value.copy(
                                history = currentSate.history.append(
                                    HistoryView("<font color='#23FF90'>$it</font>")
                                )
                            )
                        }
                    }.onFailure { failure ->
                        failure.message?.let {
                            _state.value = state.value.copy(
                                history = currentSate.history.append(
                                    HistoryView("<font color='#ff5555'>$it</font>")
                                )
                            )
                        }
                    }
                }
        }
    }


}

