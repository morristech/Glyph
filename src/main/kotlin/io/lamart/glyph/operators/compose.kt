package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

fun <T, R> Glyph<T>.compose(
        map: T.() -> R,
        reduce: T.(R) -> T
): Glyph<R> = ComposeGlyph(this, map, reduce)

fun <T, R> Glyph<T>.compose(
        map: T.() -> R,
        reduce: T.(R) -> T,
        init: Glyph<R>.() -> Unit
) = compose(map, reduce).run(init)


fun <T, R> OptionalGlyph<T>.compose(
        map: T.() -> R,
        reduce: T.(R) -> T
): OptionalGlyph<R> = ComposeOptionalGlyph(this, map, reduce)

fun <T, R> OptionalGlyph<T>.compose(
        map: T.() -> R,
        reduce: T.(R) -> T,
        init: OptionalGlyph<R>.() -> Unit
) = compose(map, reduce).run(init)

class ComposeGlyph<T, R>(
        private val glyph: Glyph<T>,
        private val map: T.() -> R,
        private val reduce: T.(R) -> T
) : Glyph<R> {

    override fun getState(): R = glyph.getState().let(map)

    override fun setState(state: R) =
            glyph.getState().let { reduce(it, state) }.let(glyph::setState)

}

class ComposeOptionalGlyph<T, R>(
        private val glyph: OptionalGlyph<T>,
        private val map: T.() -> R,
        private val reduce: T.(R) -> T
) : OptionalGlyph<R> {

    override fun getState(): R? = glyph.getState()?.let(map)

    override fun setState(state: R) {
        glyph.getState()?.let { reduce(it, state) }?.let(glyph::setState)
    }

}