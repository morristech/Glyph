package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph
import io.lamart.glyph.Transformer
import io.lamart.glyph.observable.Observable

internal class FilterGlyph<T>(
        private val glyph: Glyph<T>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> {

    override val observable: Observable<T?> =
            glyph.observable.map { state ->
                when {
                    predicate(state) -> state
                    else -> null
                }
            }

    override fun get(): T? = glyph.get().takeIf(predicate)

    override fun set(state: T) = glyph.set(state)

    override fun set(transform: Transformer<T>) =
            glyph.set {
                when {
                    predicate(it) -> transform(it)
                    else -> it
                }
            }


}

internal class FilterOptionalGlyph<T>(
        private val glyph: OptionalGlyph<T>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> {

    override val observable: Observable<T?> =
            glyph.observable.map { state ->
                when {
                    state != null && predicate(state) -> state
                    else -> null
                }
            }

    override fun get(): T? = glyph.get()?.takeIf(predicate)

    override fun set(state: T) = glyph.set(state)

    override fun set(transform: Transformer<T>) =
            glyph.set { state ->
                when {
                    predicate(state) -> transform(state)
                    else -> state
                }
            }


}