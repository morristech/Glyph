package io.lamart.glyph.operators.collections

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph
import io.lamart.glyph.observable.Observable

fun <T> Glyph<Collection<T>>.composeCollection(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeCollectionGlyph(this, predicate)

fun <T> OptionalGlyph<Collection<T>>.composeCollection(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeCollectionOptionalGlyph(this, predicate)

class ComposeCollectionGlyph<T>(
        private val glyph: Glyph<Collection<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> by ComposeCollectionOptionalGlyph(glyph.toOptional(), predicate)

open class ComposeCollectionOptionalGlyph<T>(
        private val glyph: OptionalGlyph<Collection<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> {

    override fun observe(): Observable<T?> = glyph.observe().map { it?.find(predicate) }

    override fun get(): T? = glyph.get()?.find(predicate)

    override fun set(state: T) {
        glyph.reduce {
            toMutableSet().apply {
                if (removeIf(predicate))
                    add(state)
            }
        }
    }

}

