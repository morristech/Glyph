package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

fun <T> Glyph<T>.intercept(
        getState: Glyph<T>.() -> T = { getState() },
        setState: Glyph<T>.(T) -> Unit = { it -> setState(it) }
): Glyph<T> = InterceptGlyph(this, getState, setState)

private class InterceptGlyph<T>(
        private val glyph: Glyph<T>,
        private val getState: Glyph<T>.() -> T,
        private val setState: Glyph<T>.(T) -> Unit
) : Glyph<T> {

    override fun getState(): T = getState(glyph)

    override fun setState(state: T) = setState(glyph, state)

}

fun <T> OptionalGlyph<T>.intercept(
        getState: OptionalGlyph<T>.() -> T? = { getState() },
        setState: OptionalGlyph<T>.(T) -> Unit = { it -> setState(it) }
): OptionalGlyph<T> = InterceptOptionalGlyph(this, getState, setState)

private class InterceptOptionalGlyph<T>(
        private val glyph: OptionalGlyph<T>,
        private val getState: OptionalGlyph<T>.() -> T?,
        private val setState: OptionalGlyph<T>.(T) -> Unit
) : OptionalGlyph<T> {

    override fun getState(): T? = getState(glyph)

    override fun setState(state: T) = setState(glyph, state)

}

