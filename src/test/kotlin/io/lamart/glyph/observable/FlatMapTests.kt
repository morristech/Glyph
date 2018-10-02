package io.lamart.glyph.observable

import io.lamart.glyph.implementation.SimpleGlyph
import io.lamart.glyph.observable.emitter.ListEmitter
import org.junit.Test

class FlatMapTests {

    @Test
    fun testIterable() {
        val emitter = ListEmitter<String>()
        val glyph = SimpleGlyph("", emitter)

        emitter.flatMap { state -> state.toInt().let { listOf(it, it) } }
                .test()
                .assertValueAt(0) { it == 1 }
                .assertValueAt(1) { it == 1 }

        glyph.set("1")
    }

}