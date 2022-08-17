package com.lchau.tkvstore.domain.usecase

import app.cash.turbine.test
import com.lchau.tkvstore.domain.data.KeyValueStoreRepository
import com.lchau.tkvstore.domain.error.UnknownCommandException
import com.lchau.tkvstore.domain.model.Command.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import junit.framework.TestCase.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ExecuteCommandUseCaseImplTest {
    private val getInput = "get foo"
    private val setInput = "set foo bar"
    private val deleteInput = "delete foo"
    private val countInput = "count foo"
    private val beginInput = "begin"
    private val commitInput = "commit"
    private val rollbackInput = "rollback"

    private val getCommand = Get("foo")
    private val setCommand = Set("foo", "bar")
    private val deleteCommand = Delete("foo")
    private val countCommand = Count("foo")
    private val beginCommand = Begin
    private val commitCommand = Commit
    private val rollbackCommand = Rollback

    private val repository: KeyValueStoreRepository = mockk(relaxed = true)
    private val parserUseCase: ParserUseCase = mockk(relaxed = true)

    private lateinit var testedClass: ExecuteCommandUseCaseImpl

    @Before
    fun setUp() {
        testedClass = ExecuteCommandUseCaseImpl(
            repository = repository,
            parserUseCase = parserUseCase,
            dispatcher = UnconfinedTestDispatcher()
        )
    }


    @Test
    fun `given valid Get input when execute then execute expected get on store`() =
        runTest {

            //given
            coEvery { parserUseCase.parse(getInput) } returns getCommand

            // when
            testedClass.execute(getInput).test {

                // then
                coVerify(exactly = 1) {
                    repository.get("foo")
                }
                cancelAndConsumeRemainingEvents()

            }

        }

    @Test
    fun `given valid Set input when execute then execute expected set on store`() = runTest {

        //given
        coEvery { parserUseCase.parse(setInput) } returns setCommand

        // when
        testedClass.execute(setInput).test {

            // then
            coVerify(exactly = 1) {
                repository.set("foo", "bar")
            }
            cancelAndConsumeRemainingEvents()

        }

    }

    @Test
    fun `given valid Delete input when execute then execute expected set on store`() = runTest {

        //given
        coEvery { parserUseCase.parse(deleteInput) } returns deleteCommand

        // when
        testedClass.execute(deleteInput).test {
            // then
            coVerify(exactly = 1) {
                repository.delete("foo")
            }
            cancelAndConsumeRemainingEvents()

        }

    }

    @Test
    fun `given valid Count input when execute then execute expected count on store`() = runTest {
        //given
        coEvery { parserUseCase.parse(countInput) } returns countCommand

        // when
        testedClass.execute(countInput).test {

            // then
            coVerify(exactly = 1) {
                repository.count("foo")
            }

            cancelAndConsumeRemainingEvents()

        }

    }

    @Test
    fun `given valid Begin input when execute then execute expected beginTransaction on store`() =
        runTest {

            //given
            coEvery { parserUseCase.parse(beginInput) } returns beginCommand

            // when
            testedClass.execute(beginInput).test {

                // then
                coVerify(exactly = 1) {
                    repository.beginTransaction()
                }

                cancelAndConsumeRemainingEvents()

            }


        }

    @Test
    fun `given valid Commit input when execute then execute expected commitTransaction on store`() =
        runTest {

            //given
            coEvery { parserUseCase.parse(commitInput) } returns commitCommand

            // when
            testedClass.execute(commitInput).test {

                // then
                coVerify(exactly = 1) {
                    repository.commitTransaction()
                }

                cancelAndConsumeRemainingEvents()

            }

        }

    @Test
    fun `given valid Rollback input when execute then execute expected rollbackTransaction on store`() =
        runTest {
            //given
            coEvery { parserUseCase.parse(rollbackInput) } returns rollbackCommand

            // when
            testedClass.execute(rollbackInput).test {
                // then
                coVerify(exactly = 1) {
                    repository.rollbackTransaction()
                }
                cancelAndConsumeRemainingEvents()

            }


        }


    @Test
    fun `given parserUseCase returns UnknownCommandException when execute then throw UnknownCommandException`() =
        runTest {
            // given
            coEvery { parserUseCase.parse(getInput) } throws UnknownCommandException()

            // when
            testedClass.execute(getInput).test {
                // then
                coVerify(exactly = 0) {
                    repository.get("foo")
                }

                assertEquals(
                    Result.failure<String?>(UnknownCommandException())
                        .exceptionOrNull()?.message, awaitItem().exceptionOrNull()?.message
                )

                cancelAndConsumeRemainingEvents()

            }
        }


}