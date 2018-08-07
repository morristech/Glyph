package io.lamart.glyph

import io.lamart.glyph.implementation.SimpleGlyph
import io.lamart.glyph.operators.*
import kotlin.properties.ReadWriteProperty

fun <T> T.toGlyph(): Glyph<T> = SimpleGlyph(this)

interface Glyph<T> : GlyphSource<T> {

    fun <R : T> cast(): Glyph<R> = CastGlyph(this)

    fun <R> compose(
            map: T.() -> R,
            reduce: T.(R) -> T
    ): Glyph<R> = ComposeGlyph(this, map, reduce)

    fun filter(predicate: T.(T) -> Boolean): OptionalGlyph<T> =
            FilterGlyph(this, predicate)

    fun intercept(
            getState: Glyph<T>.() -> T = { get() },
            setState: Glyph<T>.(T) -> Unit = { it -> set(it) }
    ): Glyph<T> = InterceptGlyph(this, getState, setState)

    fun listen(
            getState: (T) -> Unit = { },
            setState: (T) -> Unit = { }
    ): Glyph<T> = ListenGlyph(this, getState, setState)

    fun toOptional(): OptionalGlyph<T> = ToOptional(this)

    fun toProperty(): ReadWriteProperty<Any, T> = ToPropertyGlyph(this)

}
