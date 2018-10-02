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

fun <K, V> Glyph<Map<K, V>>.composeMap(key: K): OptionalGlyph<V> =
        ComposeMapGlyph(this, key)

fun <K, V> OptionalGlyph<Map<K, V>>.composeMap(key: K): OptionalGlyph<V> =
        ComposeMapOptionalGlyph(this, key)

class ComposeMapGlyph<K, V>(
        private val glyph: Glyph<Map<K, V>>,
        private val key: K
) : OptionalGlyph<V> by ComposeMapOptionalGlyph(glyph.toOptional(), key)

open class ComposeMapOptionalGlyph<K, V>(
        private val glyph: OptionalGlyph<Map<K, V>>,
        private val key: K
) : OptionalGlyph<V> {

    override val observable: Observable<V?> = glyph.observable.map { map -> map?.get(key) }

    override fun get(): V? = glyph.get()?.get(key)

    override fun set(state: V) =
            glyph.set { map ->
                when {
                    map.containsKey(key) -> map.toMutableMap().apply { put(key, state) }
                    else -> map
                }
            }

    override fun set(transform: Transformer<V>) =
            glyph.set { map ->
                map[key]?.let { state ->
                    map.toMutableMap().apply {
                        put(key, transform(state))
                    }
                } ?: map
            }


}