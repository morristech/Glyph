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
import io.lamart.glyph.Transformer
import io.lamart.glyph.observable.Observable

internal class FilterGlyph<T>(
        private val glyph: Glyph<T>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> {

    override val observable: Observable<T?> =
            glyph.observable.map { state ->
                when {
                    predicate(state) -> state
                    else -> null
                }
            }

    override fun get(): T? = glyph.get().takeIf(predicate)

    override fun set(state: T) = glyph.set(state)

    override fun set(transform: Transformer<T>) =
            glyph.set {
                when {
                    predicate(it) -> transform(it)
                    else -> it
                }
            }


}

internal class FilterOptionalGlyph<T>(
        private val glyph: OptionalGlyph<T>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> {

    override val observable: Observable<T?> =
            glyph.observable.map { state ->
                when {
                    state != null && predicate(state) -> state
                    else -> null
                }
            }

    override fun get(): T? = glyph.get()?.takeIf(predicate)

    override fun set(state: T) = glyph.set(state)

    override fun set(transform: Transformer<T>) =
            glyph.set { state ->
                when {
                    predicate(state) -> transform(state)
                    else -> state
                }
            }


}