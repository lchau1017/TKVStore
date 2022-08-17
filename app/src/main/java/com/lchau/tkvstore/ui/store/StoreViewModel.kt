package com.lchau.tkvstore.ui.store

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.lchau.tkvstore.domain.usecase.ExecuteCommandUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
internal class StoreViewModel constructor(
    private val executeCommandUseCase: ExecuteCommandUseCase,
    initStateState: StoreState,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    @Inject
    constructor(
        executeCommandUseCase: ExecuteCommandUseCase,
        @Named("io") dispatcher: CoroutineDispatcher
    ) : this(
        executeCommandUseCase,
        StoreState(),
        dispatcher
    )

    private val _state = MutableStateFlow(initStateState)
    val state: StateFlow<StoreState> = _state.asStateFlow()

    fun onTextChanged(text: String) {
        dispatchIntent(StoreIntent.InputIntent(text))
    }

    fun onInputSubmitted() {
        dispatchIntent(StoreIntent.SubmitIntent)
    }

    private fun intentToAction(intent: StoreIntent): StoreAction {
        return when (intent) {
            is StoreIntent.SubmitIntent -> StoreAction.SubmitAction
            is StoreIntent.InputIntent -> StoreAction.InputAction(intent.command)
        }
    }

    private fun handleAction(storeAction: StoreAction) {
        when (storeAction) {
            is StoreAction.SubmitAction -> {
                val currentSate = state.value
                if (currentSate.inputTextToDisplay.isEmpty()) {
                    return
                }
                _state.value = currentSate.copy(
                    inputTextToDisplay = "",
                    history = currentSate.history.append(HistoryView.InputHistory(currentSate.inputTextToDisplay.trim()))
                )
                executeCommandLineInput(currentSate.inputTextToDisplay.trim())
            }
            is StoreAction.InputAction -> {
                _state.value = state.value.copy(
                    inputTextToDisplay = storeAction.command
                )
            }
        }
    }

    private fun dispatchIntent(intent: StoreIntent) {
        handleAction(intentToAction(intent))
    }

    private fun executeCommandLineInput(input: String) {
        viewModelScope.launch {
            executeCommandUseCase.execute(input)
                .flowOn(dispatcher)
                .catch {
                    it.message?.let { it1 -> Log.e("executeCommandLineInput error :", it1) }
                }
                .collectLatest { result ->
                    val currentSate = state.value
                    result.onSuccess { response ->
                        response?.let {
                            _state.value = state.value.copy(
                                history = currentSate.history.append(
                                    HistoryView.OutputHistory(
                                        it,
                                        isError = false
                                    )
                                )
                            )
                        }
                    }.onFailure { failure ->
                        failure.message?.let {
                            _state.value = state.value.copy(
                                history = currentSate.history.append(HistoryView.OutputHistory(it))
                            )
                        }
                    }
                }
        }
    }


}

