package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph
import io.lamart.glyph.observable.Observable

internal class ComposeGlyph<T, R>(
        private val glyph: Glyph<T>,
        private val map: T.() -> R,
        private val reduce: T.(R) -> T
) : Glyph<R> {

    override val observable: Observable<R> = glyph.observable.map(map)

    override fun get(): R = glyph.get().let(map)

    override fun set(state: R) = glyph.set { reduce(it, state) }

    @Suppress("ReplaceSingleLineLet")
    override fun set(transform: (R) -> R) =
            glyph.set { t: T ->
                map(t).let { r ->
                    reduce(t, transform(r))
                }
            }

}

internal class ComposeOptionalGlyph<T, R>(
        private val glyph: OptionalGlyph<T>,
        private val map: T.() -> R,
        private val reduce: T.(R) -> T
) : OptionalGlyph<R> {

    override val observable: Observable<R?> = glyph.observable.map { it?.let(map) }

    override fun get(): R? = glyph.get()?.let(map)

    override fun set(state: R) = glyph.set { reduce(it, state) }

    @Suppress("ReplaceSingleLineLet")
    override fun set(transform: (R) -> R) {
        glyph.set { t ->
            map(t).let { r ->
                reduce(t, transform(r))
            }
        }
    }

}