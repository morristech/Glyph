package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph
import io.lamart.glyph.toOptional


fun <T> Glyph<Set<T>>.composeSet(predicate: (T) -> Boolean, init: OptionalGlyph<T>.() -> Unit) =
        composeSet(predicate).run(init)

fun <T> Glyph<Set<T>>.composeSet(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeSetGlyph(this@composeSet, predicate)

fun <T> OptionalGlyph<Set<T>>.composeSet(predicate: (T) -> Boolean, init: OptionalGlyph<T>.() -> Unit) =
        composeSet(predicate).run(init)

fun <T> OptionalGlyph<Set<T>>.composeSet(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeSetOptionalGlyph(this@composeSet, predicate)

class ComposeSetGlyph<T>(
        private val glyph: Glyph<Set<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> by ComposeSetOptionalGlyph(glyph.toOptional(), predicate)

open class ComposeSetOptionalGlyph<T>(
        private val glyph: OptionalGlyph<Set<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> {

    override fun getState(): T? = glyph.getState()?.find(predicate)

    override fun setState(state: T) {
        glyph.getState()
                ?.toMutableSet()
                ?.also { replace(it, state) }
                ?.let(glyph::setState)
    }

    private fun replace(list: MutableSet<T>, state: T) =
            with(list) {
                find(predicate)?.also { remove(it); add(state) }
            }

}

