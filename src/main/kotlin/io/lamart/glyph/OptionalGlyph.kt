package io.lamart.glyph

import io.lamart.glyph.operators.*
import kotlin.properties.ReadWriteProperty

interface OptionalGlyph<T> : OptionalGlyphSource<T> {

    fun <R : T> cast(): OptionalGlyph<R> = CastOptionalGlyph(this)

    fun <R> compose(
            map: T.() -> R,
            reduce: T.(R) -> T
    ): OptionalGlyph<R> = ComposeOptionalGlyph(this, map, reduce)

    fun filter(predicate: T.(T) -> Boolean): OptionalGlyph<T> =
            FilterOptionalGlyph(this, predicate)

    fun intercept(
            getState: OptionalGlyph<T>.() -> T? = { get() },
            setState: OptionalGlyph<T>.(T) -> Unit = { state -> this.set(state) }
    ): OptionalGlyph<T> = InterceptOptionalGlyph(this, getState, setState)

    fun listen(
            getState: (T?) -> Unit = { },
            setState: (T) -> Unit = { }
    ): OptionalGlyph<T> = ListenOptionalGlyph(this, getState, setState)

    fun toProperty(): ReadWriteProperty<Any, T?> = ToPropertyOptionalGlyph(this)

}