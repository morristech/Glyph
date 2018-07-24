package io.lamart.glyph

import io.lamart.glyph.implementation.SimpleGlyph
import org.junit.Assert.assertEquals
import org.junit.Test

class ToPropertyTests {

    private var name: String by SimpleGlyph("Danny").toProperty()

    @Test
     fun test() {
        assertEquals("Danny", name)
        name = "Lamarti"
        assertEquals("Lamarti", name)
    }

}