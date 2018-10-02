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

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph
import io.lamart.glyph.observable.Observable

@Suppress("UNCHECKED_CAST")
class CastGlyph<T, R : T>(private val glyph: Glyph<T>) : Glyph<R> {

    override val observable: Observable<R> = glyph.observable.cast()

    override fun get(): R = glyph.get() as R

    override fun set(state: R) = glyph.set(state)

    override fun set(transform: (R) -> R) {
        glyph.set {
            transform(it as R)
        }
    }

}

@Suppress("UNCHECKED_CAST")
class CastOptionalGlyph<T, R : T>(private val glyph: OptionalGlyph<T>) : OptionalGlyph<R> {

    override val observable: Observable<R?> = glyph.observable.cast()

    override fun get(): R? = glyph.get() as R?

    override fun set(state: R) = glyph.set(state)

    override fun set(transform: (R) -> R) {
        glyph.set {
            transform(it as R)
        }
    }

}