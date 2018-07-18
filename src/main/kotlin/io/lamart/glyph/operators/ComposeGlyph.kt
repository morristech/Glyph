package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

internal class ComposeGlyph<T, R>(
        private val glyph: Glyph<T>,
        private val map: T.() -> R,
        private val reduce: T.(R) -> T
) : Glyph<R> {

    override fun getState(): R = glyph.getState().let(map)

    override fun setState(state: R) =
            glyph.getState().let { reduce(it, state) }.let(glyph::setState)

}

internal class ComposeOptionalGlyph<T, R>(
        private val glyph: OptionalGlyph<T>,
        private val map: T.() -> R,
        private val reduce: T.(R) -> T
) : OptionalGlyph<R> {

    override fun getState(): R? = glyph.getState()?.let(map)

    override fun setState(state: R) {
        glyph.getState()?.let { reduce(it, state) }?.let(glyph::setState)
    }

}