package com.lchau.tkvstore.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun <T> rememberStateFlow(
    flow: StateFlow<T>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    scope: CoroutineScope,
    initial: T
): StateFlow<T> {
    return remember(key1 = flow, key2 = lifecycleOwner) {
        flow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .stateIn(scope, SharingStarted.WhileSubscribed(5000), initial)
    }
}

@Composable
fun <T : R, R> StateFlow<T>.collectAsStateLifecycleAware(
    initial: R,
    scope: CoroutineScope = CoroutineScope(EmptyCoroutineContext)
): State<R> {
    val lifecycleAwareFlow =
        rememberStateFlow(flow = this, scope = scope, initial = initial)
    return lifecycleAwareFlow.collectAsState(initial = initial, context = scope.coroutineContext)
}