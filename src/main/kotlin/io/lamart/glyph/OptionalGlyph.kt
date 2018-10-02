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

import io.lamart.glyph.observable.Observable
import io.lamart.glyph.operators.CastOptionalGlyph
import io.lamart.glyph.operators.ComposeOptionalGlyph
import io.lamart.glyph.operators.FilterOptionalGlyph
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface OptionalGlyph<T> : ReadWriteProperty<Any, T?> {

    val observable: Observable<T?>

    fun get(): T?

    fun set(state: T)

    fun set(transform: Transformer<T>)

    fun <R : T> cast(): OptionalGlyph<R> = CastOptionalGlyph(this)

    fun <R> compose(
            map: T.() -> R,
            reduce: T.(R) -> T
    ): OptionalGlyph<R> = ComposeOptionalGlyph(this, map, reduce)

    fun filter(predicate: T.(T) -> Boolean): OptionalGlyph<T> =
            FilterOptionalGlyph(this) { predicate(it, it) }

    override fun getValue(thisRef: Any, property: KProperty<*>): T? = get()

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        if (value != null)
            set(value)
    }

}