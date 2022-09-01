package com.lchau.tkvstore.domain.usecase

import com.lchau.tkvstore.domain.error.UnknownCommandException
import com.lchau.tkvstore.domain.model.Command.*
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ParserUseCaseTest {

    private lateinit var testedClass: ParserUseCase

    @Before
    fun setUp() {
        testedClass = ParserUseCase()
    }

    @Test(expected = UnknownCommandException::class)
    fun `given input with more than 3 tokens when execute then throw UnknownCommandException`() {
        // given
        val input = "set fus do rah"

        // when/then
        testedClass.parse(input)
    }

    @Test(expected = UnknownCommandException::class)
    fun `given input with 1 token with unknown command when execute then throw UnknownCommandException`() {
        // given
        val input = "fus"

        // when/then
        testedClass.parse(input)
    }

    @Test(expected = UnknownCommandException::class)
    fun `given input with 2 tokens with unknown command when execute then throw UnknownCommandException`() {
        // given
        val input = "fus do"

        // when/then
        testedClass.parse(input)
    }

    @Test
    fun `given input with valid get tokens when execute then return Get command`() {
        // given
        val input = "get foo"
        val expected = Get("foo")
        // when
        val result = testedClass.parse(input)
        //then
        assertEquals(expected, result)

    }

    @Test
    fun `given input with valid set tokens when execute then return Set command`() {
        // given
        val input = "set foo bar"
        val expected = Set("foo", "bar")
        // when
        val result = testedClass.parse(input)
        //then
        assertEquals(expected, result)
    }

    @Test
    fun `given input with valid delete tokens when execute then return Delete command`() {
        // given
        val input = "delete foo"
        val expected = Delete("foo")
        // when
        val result = testedClass.parse(input)
        //then
        assertEquals(expected, result)
    }

    @Test
    fun `given input with valid count tokens when execute then return Count command`() {
        // given
        val input = "count foo"
        val expected = Count("foo")
        // when
        val result = testedClass.parse(input)
        //then
        assertEquals(expected, result)
    }

    @Test
    fun `given input with valid begin token when execute then return Begin command`() {
        // given
        val input = "begin"
        val expected = Begin
        // when
        val result = testedClass.parse(input)
        //then
        assertEquals(expected, result)
    }

    @Test
    fun `given input with valid commit token when execute then return Commit command`() {
        // given
        val input = "commit"
        val expected = Commit
        // when
        val result = testedClass.parse(input)
        //then
        assertEquals(expected, result)
    }

    @Test
    fun `given input with valid commit token when execute then return Rollback command`() {
        // given
        val input = "rollback"
        val expected = Rollback
        // when
        val result = testedClass.parse(input)
        //then
        assertEquals(expected, result)
    }

    @Test
    fun `given valid command input with multiple spaces between tokens when execute then return expected command`() {
        // given
        val input = "get         foo"
        val expected = Get("foo")
        // when
        val result = testedClass.parse(input)
        //then
        assertEquals(expected, result)
    }

    @Test
    fun `given valid command input command token in caps when execute then return expected command`() {
        // given
        val input = "GET foo"
        val expected = Get("foo")
        // when
        val result = testedClass.parse(input)
        //then
        assertEquals(expected, result)
    }
}