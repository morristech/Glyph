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

internal class ComposeGlyph<T, R>(
        private val glyph: Glyph<T>,
        private val map: T.() -> R,
        private val reduce: T.(R) -> T
) : Glyph<R> {

    override val observable: Observable<R> = glyph.observable.map(map)

    override fun get(): R = glyph.get().let(map)

    override fun set(state: R) = glyph.set { reduce(it, state) }

    @Suppress("ReplaceSingleLineLet")
    override fun set(transform: (R) -> R) =
            glyph.set { t: T ->
                map(t).let { r ->
                    reduce(t, transform(r))
                }
            }

}

internal class ComposeOptionalGlyph<T, R>(
        private val glyph: OptionalGlyph<T>,
        private val map: T.() -> R,
        private val reduce: T.(R) -> T
) : OptionalGlyph<R> {

    override val observable: Observable<R?> = glyph.observable.map { it?.let(map) }

    override fun get(): R? = glyph.get()?.let(map)

    override fun set(state: R) = glyph.set { reduce(it, state) }

    @Suppress("ReplaceSingleLineLet")
    override fun set(transform: (R) -> R) {
        glyph.set { t ->
            map(t).let { r ->
                reduce(t, transform(r))
            }
        }
    }

}