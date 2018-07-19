package io.lamart.glyph.operators.collections

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

fun <T> Glyph<Set<T>>.composeSet(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeSetGlyph(this@composeSet, predicate)

fun <T> OptionalGlyph<Set<T>>.composeSet(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeSetOptionalGlyph(this@composeSet, predicate)

class ComposeSetGlyph<T>(
        private val glyph: Glyph<Set<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> by ComposeSetOptionalGlyph(glyph.asOptional(), predicate)

open class ComposeSetOptionalGlyph<T>(
        private val glyph: OptionalGlyph<Set<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> {

    override fun get(): T? = glyph.get()?.find(predicate)

    override fun set(state: T) {
        glyph.get()
                ?.toMutableSet()
                ?.also { replace(it, state) }
                ?.let(glyph::set)
    }

    private fun replace(list: MutableSet<T>, state: T) =
            with(list) {
                find(predicate)?.also { remove(it); add(state) }
            }

}

