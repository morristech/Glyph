package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph
import io.lamart.glyph.toOptional


fun <T> Glyph<List<T>>.composeList(predicate: (T) -> Boolean, init: OptionalGlyph<T>.() -> Unit) =
        composeList(predicate).run(init)

fun <T> Glyph<List<T>>.composeList(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeListGlyph(this@composeList, predicate)

fun <T> OptionalGlyph<List<T>>.composeList(predicate: (T) -> Boolean, init: OptionalGlyph<T>.() -> Unit) =
        composeList(predicate).run(init)

fun <T> OptionalGlyph<List<T>>.composeList(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeListOptionalGlyph(this@composeList, predicate)

class ComposeListGlyph<T>(
        private val glyph: Glyph<List<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> by ComposeListOptionalGlyph(glyph.toOptional(), predicate)

open class ComposeListOptionalGlyph<T>(
        private val glyph: OptionalGlyph<List<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> {

    override fun getState(): T? = glyph.getState()?.find(predicate)

    override fun setState(state: T) {
        glyph.getState()
                ?.toMutableList()
                ?.also { replace(it, state) }
                ?.let(glyph::setState)
    }

    private fun replace(list: MutableList<T>, state: T) =
            with(list) {
                indexOfFirst(predicate)
                        .takeIf { it != -1 }
                        ?.also { index -> set(index, state) }
            }

}

