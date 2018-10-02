package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph
import io.lamart.glyph.Transformer
import io.lamart.glyph.observable.Observable

fun <T> Glyph<Set<T>>.composeSet(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeSetGlyph(this, predicate)

fun <T> OptionalGlyph<Set<T>>.composeSet(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeSetOptionalGlyph(this, predicate)

class ComposeSetGlyph<T>(
        private val glyph: Glyph<Set<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> by ComposeSetOptionalGlyph(glyph.toOptional(), predicate)

open class ComposeSetOptionalGlyph<T>(
        private val glyph: OptionalGlyph<Set<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> {

    override val observable: Observable<T?> = glyph.observable.map { it?.find(predicate) }

    override fun get(): T? = glyph.get()?.find(predicate)

    override fun set(state: T) = set { state }

    override fun set(transform: Transformer<T>) =
            glyph.set { set ->
                set.find(predicate)?.let { state ->
                    set.toMutableSet().apply {
                        transform(state).also { next ->
                            remove(state)
                            add(next)
                        }
                    }
                } ?: set
            }

}

