package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph
import io.lamart.glyph.observable.Observable

internal class ComposeGlyph<T, R>(
        private val glyph: Glyph<T>,
        private val map: T.() -> R,
        private val reduce: T.(R) -> T
) : Glyph<R> {

    override fun observe(): Observable<R> = glyph.observe().map(map)

    override fun get(): R = glyph.get().let(map)

    override fun set(state: R) = glyph.reduce { reduce(it, state) }

}

internal class ComposeOptionalGlyph<T, R>(
        private val glyph: OptionalGlyph<T>,
        private val map: T.() -> R,
        private val reduce: T.(R) -> T
) : OptionalGlyph<R> {

    override fun observe(): Observable<R?> =
            glyph.observe().map { it?.let(map) }

    override fun get(): R? = glyph.get()?.let(map)

    override fun set(state: R) = glyph.reduce { reduce(it, state) }

}