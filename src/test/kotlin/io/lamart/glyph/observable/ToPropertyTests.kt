package io.lamart.glyph.observable

import io.lamart.glyph.toGlyph
import org.junit.Assert.assertEquals
import org.junit.Test

class ToPropertyTests {

    private var name: String by "Danny".toGlyph().asProperty()

    @Test
     fun test() {
        assertEquals("Danny", name)
        name = "Lamarti"
        assertEquals("Lamarti", name)
    }

}