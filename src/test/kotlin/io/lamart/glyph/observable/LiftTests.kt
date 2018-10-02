package io.lamart.glyph.observable

import io.lamart.glyph.Observer
import io.lamart.glyph.implementation.SimpleGlyph
import io.lamart.glyph.observable.emitter.ListEmitter
import org.junit.Test

class LiftTests {

    @Test
    fun test() {
        val emitter = ListEmitter<String>()
        val glyph = SimpleGlyph("", emitter)

        emitter
                .intercept(Lifter)
                .test()
                .assertValueAt(0) { it == 1 }
                .assertValueAt(1) { it == 1 }

        glyph.set("1")
    }

    private object Lifter : (Observer<Int>) -> Observer<String> {

        override fun invoke(observer: Observer<Int>): Observer<String> =
                {
                    it.toInt().let(observer)
        }

    }

}