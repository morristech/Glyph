/*
 * Copyright (c) 2018 Danny Lamarti.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.lamart.glyph

import io.lamart.glyph.emitter.Emitter
import io.lamart.glyph.emitter.SynchronizedListEmitter
import io.lamart.glyph.observable.Observable


class SynchronizedGlyph<T>(
        private var state: T,
        private val emitter: Emitter<T> = SynchronizedListEmitter()
) : Glyph<T> {

    val lock: Any = Any()

    override val observable: Observable<T> = emitter

    override fun get(): T = synchronized(lock) { state }

    override fun set(state: T) {
        synchronized(lock) {
            this.state = state
        }
        emitter(state)
    }

    override fun set(transform: Transformer<T>) {
        synchronized(lock) {
            state = transform(state)
        }
        emitter(state)
    }

    fun synchronized(block: Glyph<T>.() -> Unit) = synchronized(lock) { block() }

}