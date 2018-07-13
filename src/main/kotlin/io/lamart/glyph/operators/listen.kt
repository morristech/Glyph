package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

fun <T> Glyph<T>.listen(
        getState: (T) -> Unit = { },
        setState: (T) -> Unit = { }
): Glyph<T> = ListenGlyph(this, getState, setState)

private class ListenGlyph<T>(
        private val glyph: Glyph<T>,
        private val getState: (T) -> Unit,
        private val setState: (T) -> Unit
) : Glyph<T> {

    override fun getState(): T = glyph.getState().also(getState)

    override fun setState(state: T) {
        glyph.setState(state)
        setState.invoke(state)
    }

}

fun <T> OptionalGlyph<T>.listen(
        getState: (T?) -> Unit = { },
        setState: (T) -> Unit = { }
): OptionalGlyph<T> = ListenOptionalGlyph(this, getState, setState)

private class ListenOptionalGlyph<T>(
        private val glyph: OptionalGlyph<T>,
        private val getState: (T?) -> Unit,
        private val setState: (T) -> Unit
) : OptionalGlyph<T> {

    override fun getState(): T? = glyph.getState().also(getState)

    override fun setState(state: T) {
        glyph.setState(state)
        setState.invoke(state)
    }

}