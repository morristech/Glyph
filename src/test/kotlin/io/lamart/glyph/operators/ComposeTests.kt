package io.lamart.glyph.operators

import io.lamart.glyph.implementation.SimpleGlyph
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ComposeTests {

    @Test
    fun compose() {
        val glyph = SimpleGlyph(1 to "a")

        glyph.compose({ second }, { copy(second = it) }).set("b")
        assertEquals(1 to "b", glyph.get())
    }

    @Test
    fun composeList() {
        val glyph = SimpleGlyph(listOf(1, 2, 3))

        glyph.composeList { it == 2 }.set(4)
        assertEquals(4, glyph.get()[1])
    }

    @Test
    fun composeSet() {
        val glyph = SimpleGlyph(setOf(1, 2, 3))

        glyph.composeSet { it == 2 }.set(4)
        glyph.get().contains(4).let(::assertTrue)

    }

    @Test
    fun composeCollection() {
        val glyph = SimpleGlyph<Collection<Int>>(setOf(1, 2, 3))

        glyph.composeCollection { it == 2 }.set(4)
        glyph.get().contains(4).let(::assertTrue)
    }

    @Test
    fun composeMap() {
        val glyph = SimpleGlyph(mapOf(
                1 to "a",
                2 to "b",
                3 to "c"
        ))

        glyph.composeMap(2).set("d")
        assertEquals("d", glyph.get()[2])
    }

}