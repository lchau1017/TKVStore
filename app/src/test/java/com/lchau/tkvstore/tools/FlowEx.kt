package com.lchau.tkvstore.tools

import app.cash.turbine.FlowTurbine
import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> StateFlow<T>.collectForTesting(testBlock: suspend FlowTurbine<T>.() -> Unit) {
    runTest {
        test {
            testBlock()
        }
    }
}
