package com.lchau.tkvstore.data.repository

import com.lchau.tkvstore.domain.model.Transaction
import com.lchau.tkvstore.domain.data.Store
import com.lchau.tkvstore.domain.error.KeyNotSetException
import com.lchau.tkvstore.domain.error.NoTransactionException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import java.util.ArrayDeque

@OptIn(ExperimentalCoroutinesApi::class)
class KeyValueStoreRepositoryImplTest {

    private val store: Store = mockk(relaxed = true)

    private lateinit var testedClass: KeyValueStoreRepositoryImpl

    @Before
    fun setUp() {
        testedClass = KeyValueStoreRepositoryImpl(
            store = store,
            transactionStack = ArrayDeque<Transaction>()
        )
    }

    @Test(expected = KeyNotSetException::class)
    fun `given key does not exist when get then throw KeyNotSetException`() = runTest {

        //given
        coEvery { store.get("foo") } returns null

        //when
        testedClass.get("foo")

        ///then
        coVerify(exactly = 1) {
            store.get("foo")
        }

    }

    @Test
    fun `given key does exists when get then return value`() = runTest {
        //given
        coEvery { store.get("foo") } returns "bar"

        //when
        val result = testedClass.get("foo")

        ///then
        coVerify(exactly = 2) {
            store.get("foo")
        }

        assertEquals("bar", result)
    }


    @Test(expected = KeyNotSetException::class)
    fun `given key does not exist when delete then throw KeyNotSetException`() = runTest {
        // when
        testedClass.delete("foo")
        ///then
        coVerify(exactly = 1) {
            store.delete("foo")
        }
    }

    @Test
    fun `given key does exists when delete then return value`() = runTest {
        //given
        coEvery { store.delete("foo") } returns "bar"
        // when
        testedClass.delete("foo")
        ///then
        coVerify(exactly = 1) {
            store.delete("foo")
        }
    }

    @Test(expected = Test.None::class)
    fun `when beginTransaction is called multiple times then there is no exception thrown`() =
        runTest {
            // when/then
            testedClass.beginTransaction()
            testedClass.beginTransaction()
            testedClass.beginTransaction()
        }

    @Test(expected = NoTransactionException::class)
    fun `given no started transactions when commitTransaction then throw NoTransactionException`() =
        runTest {
            // when/then
            testedClass.commitTransaction()
        }

    @Test(expected = Test.None::class)
    fun `given a started transactions when commitTransaction throw then no exception thrown`() =
        runTest {
            // given
            testedClass.beginTransaction()

            // when/then
            testedClass.commitTransaction()
        }


    @Test(expected = NoTransactionException::class)
    fun `given no started transactions when rollbackTransaction then throw NoTransactionException`() =
        runTest {
            // when/then
            testedClass.rollbackTransaction()
        }

    @Test(expected = Test.None::class)
    fun `given a started transactions when rollbackTransaction then throw no exceptions`() =
        runTest {
            // given
            testedClass.beginTransaction()

            // when/then
            testedClass.rollbackTransaction()
        }

}