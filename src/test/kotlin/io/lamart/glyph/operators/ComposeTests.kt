/*
 * Copyright (c) 2018 Danny Lamarti.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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