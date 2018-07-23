package io.lamart.glyph

import io.lamart.glyph.operators.*

interface Glyph<T> : GlyphSource<T> {

    fun <R : T> check(predicate: T.(T) -> Boolean): OptionalGlyph<R> =
            CheckGlyph(this, predicate)

    fun <R> compose(
            map: T.() -> R,
            reduce: T.(R) -> T
    ): Glyph<R> = ComposeGlyph(this, map, reduce)

    fun filter(predicate: T.(T) -> Boolean): OptionalGlyph<T> =
            FilterGlyph(this, predicate)

    fun intercept(
            getState: Glyph<T>.() -> T = { get() },
            setState: Glyph<T>.(T) -> Unit = { it -> this.set(it) }
    ): Glyph<T> = InterceptGlyph(this, getState, setState)

    fun listen(
            getState: (T) -> Unit = { },
            setState: (T) -> Unit = { }
    ): Glyph<T> = ListenGlyph(this, getState, setState)

    fun toOptional(): OptionalGlyph<T> = ToOptionalGlyph(this)

}


