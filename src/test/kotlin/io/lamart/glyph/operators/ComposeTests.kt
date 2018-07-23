package io.lamart.glyph.operators

import io.lamart.glyph.implementation.SimpleGlyph
import io.lamart.glyph.operators.collections.composeCollection
import io.lamart.glyph.operators.collections.composeList
import io.lamart.glyph.operators.collections.composeMap
import io.lamart.glyph.operators.collections.composeSet
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ComposeTests {

    @Test
    fun compose() {
        val glyph = SimpleGlyph(1 to "a")

        glyph.compose({ second }, { copy(second = it) }).set("b")
        glyph.get().let {
            assertEquals(1 to "b", it)
        }
    }

    @Test
    fun composeList() {
        val glyph = SimpleGlyph(listOf(1, 2, 3))

        glyph.composeList { it == 2 }.set(4)
        glyph.get()[1].let {
            assertEquals(4, it)
        }
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
        glyph.get()[2].let {
            assertEquals("d", it)
        }
    }

}