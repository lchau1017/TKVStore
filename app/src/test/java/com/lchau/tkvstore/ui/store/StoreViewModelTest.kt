package com.lchau.tkvstore.ui.store

import com.lchau.tkvstore.tools.CoroutineTestRule
import com.lchau.tkvstore.tools.collectForTesting
import com.lchau.tkvstore.domain.error.UnknownCommandException
import com.lchau.tkvstore.domain.usecase.ExecuteCommandUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StoreViewModelTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private val executeCommandUseCase: ExecuteCommandUseCase =
        mockk(relaxed = true)

    private lateinit var testedClass: StoreViewModel

    @Test
    fun `when get initial viewModel state then state is the default state`() =
        runTest {
            //given
            testedClass = StoreViewModel(
                executeCommandUseCase,
                coroutineTestRule.testDispatcher
            )
            // when // then
            testedClass.state.collectForTesting {
                assertEquals(StoreState(), awaitItem())
            }
        }

    @Test
    fun `given valid input text and inputEnabled is false when onInputSubmitted then ignore input and not execute input`() =
        runTest {
            //given
            testedClass = StoreViewModel(
                executeCommandUseCase,
                StoreState(),
                coroutineTestRule.testDispatcher
            )

            testedClass.state.collectForTesting {

                // when
                testedClass.onInputSubmitted()

                // then
                assertEquals(StoreState(), awaitItem())

                coVerify(exactly = 0) {
                    executeCommandUseCase.execute("")
                }

            }
        }

    @Test
    fun `given valid input text when onInputSubmitted then add input to history`() = runTest {

        //given
        val getInput = "Get foo"

        //given
        testedClass = StoreViewModel(
            executeCommandUseCase,
            StoreState(getInput),
            coroutineTestRule.testDispatcher
        )


        testedClass.state.collectForTesting {
            // when
            testedClass.onInputSubmitted()

            // then

            assertEquals(
                StoreState(getInput), awaitItem()
            )
            assertEquals(
                StoreState(
                    history = listOf(HistoryView.InputHistory(getInput))
                ), awaitItem()
            )

            cancelAndConsumeRemainingEvents()

        }
    }


    @Test
    fun `given valid input when onInputSubmitted then attempt to execute input as a command`() =
        runTest {

            val getInput = "Get foo"

            //given
            testedClass = StoreViewModel(
                executeCommandUseCase,
                StoreState(getInput),
                coroutineTestRule.testDispatcher
            )

            testedClass.onInputSubmitted()

            // then

            coVerify(exactly = 1) {
                executeCommandUseCase.execute(getInput)
            }
        }


    @Test
    fun `given valid input when onInputSubmitted executes command that throws UnknownCommandException exception then add expected error response to history`() =
        runTest {

            //given
            val input = "ff"
            coEvery { executeCommandUseCase.execute(input) } returns flow {
                emit(
                    Result.failure<String>(
                        UnknownCommandException()
                    )
                )
            }

            //given
            testedClass = StoreViewModel(
                executeCommandUseCase,
                StoreState(input),
                coroutineTestRule.testDispatcher
            )


            testedClass.state.collectForTesting {
                // when
                testedClass.onInputSubmitted()

                // then
                assertEquals(
                    StoreState(input), awaitItem()
                )

                assertEquals(
                    StoreState(
                        history = listOf(HistoryView.InputHistory(input))
                    ), awaitItem()
                )

                assertEquals(
                    StoreState(
                        history = listOf(
                            HistoryView.InputHistory(input), HistoryView.OutputHistory(
                                UnknownCommandException().message!!,
                            )
                        )

                    ), awaitItem()
                )

                cancelAndConsumeRemainingEvents()

            }
        }

}