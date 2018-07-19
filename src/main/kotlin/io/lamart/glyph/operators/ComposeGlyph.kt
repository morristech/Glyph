package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

internal class ComposeGlyph<T, R>(
        private val glyph: Glyph<T>,
        private val map: T.() -> R,
        private val reduce: T.(R) -> T
) : Glyph<R> {

    override fun get(): R = glyph.get().let(map)

    override fun set(state: R) =
            glyph.get().let { reduce(it, state) }.let(glyph::set)

}

internal class ComposeOptionalGlyph<T, R>(
        private val glyph: OptionalGlyph<T>,
        private val map: T.() -> R,
        private val reduce: T.(R) -> T
) : OptionalGlyph<R> {

    override fun get(): R? = glyph.get()?.let(map)

    override fun set(state: R) {
        glyph.get()?.let { reduce(it, state) }?.let(glyph::set)
    }

}