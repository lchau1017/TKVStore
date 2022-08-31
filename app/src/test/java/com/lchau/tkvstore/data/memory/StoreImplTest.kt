package com.lchau.tkvstore.data.memory

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class StoreImplTest {

    val testedClass = StoreImpl()

    @Test
    fun test_filter() = runTest {

        //given
        testedClass.set("f1", "1")
        testedClass.set("f2", "2")
        testedClass.set("f3", "3")

        val expected = listOf("1", "2", "3")

        Assert.assertEquals(expected, testedClass.filter("f"))

    }

}