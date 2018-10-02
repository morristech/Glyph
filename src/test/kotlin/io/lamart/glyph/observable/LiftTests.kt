/*
 * Copyright (c) 2018 Danny Lamarti.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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