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

import io.lamart.glyph.implementation.SimpleGlyph
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.operators.CastGlyph
import io.lamart.glyph.operators.ComposeGlyph
import io.lamart.glyph.operators.FilterGlyph
import io.lamart.glyph.operators.ToOptional
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> T.toGlyph(): Glyph<T> = Glyph(this)

interface Glyph<T> : ReadWriteProperty<Any, T> {

    val observable: Observable<T>

    fun get(): T

    fun set(state: T)

    fun set(transform: Transformer<T>)

    fun <R : T> cast(): Glyph<R> = CastGlyph(this)

    fun <R> compose(
            map: T.() -> R,
            reduce: T.(R) -> T
    ): Glyph<R> = ComposeGlyph(this, map, reduce)

    fun filter(predicate: T.(T) -> Boolean): OptionalGlyph<T> =
            FilterGlyph(this) { predicate(it, it) }

    fun toOptional(): OptionalGlyph<T> = ToOptional(this)

    override fun getValue(thisRef: Any, property: KProperty<*>): T = get()

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = set(value)

    companion object {

        operator fun <T> invoke(state: T): Glyph<T> = SimpleGlyph(state)

    }

}