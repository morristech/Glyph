package io.lamart.glyph

import io.lamart.glyph.operators.*

interface Glyph<T> : GlyphSource<T> {

    fun asOptional(): OptionalGlyph<T> = AsOptionalGlyph(this)

    fun <R> compose(
            map: T.() -> R,
            reduce: T.(R) -> T
    ): Glyph<R> = ComposeGlyph(this, map, reduce)

    fun filter(predicate: T.(T) -> Boolean): OptionalGlyph<T> =
            FilterGlyph(this, predicate)

    fun intercept(
            getState: Glyph<T>.() -> T = { getState() },
            setState: Glyph<T>.(T) -> Unit = { it -> setState(it) }
    ): Glyph<T> = InterceptGlyph(this, getState, setState)

    fun listen(
            getState: (T) -> Unit = { },
            setState: (T) -> Unit = { }
    ): Glyph<T> = ListenGlyph(this, getState, setState)

    @Suppress("UNCHECKED_CAST")
    fun <R : T> to(): OptionalGlyph<R> = to { this as? R }

    fun <R : T> to(block: T.() -> R?): OptionalGlyph<R> = ToGlyph(this, block)

}


