package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

fun <K, V> Glyph<Map<K, V>>.composeMap(key: K, init: OptionalGlyph<V>.() -> Unit) =
        composeMap(key).run(init)

fun <K, V> Glyph<Map<K, V>>.composeMap(key: K): OptionalGlyph<V> =
        ComposeMapGlyph(this@composeMap, key)

fun <K, V> OptionalGlyph<Map<K, V>>.composeMap(key: K, init: OptionalGlyph<V>.() -> Unit) =
        composeMap(key).run(init)

fun <K, V> OptionalGlyph<Map<K, V>>.composeMap(key: K): OptionalGlyph<V> =
        ComposeMapOptionalGlyph(this@composeMap, key)

class ComposeMapGlyph<K, V>(
        private val glyph: Glyph<Map<K, V>>,
        private val key: K
) : OptionalGlyph<V> by ComposeMapOptionalGlyph(glyph.toOptional(), key)

open class ComposeMapOptionalGlyph<K, V>(
        private val glyph: OptionalGlyph<Map<K, V>>,
        private val key: K
) : OptionalGlyph<V> {

    override fun getState(): V? = glyph.getState()?.get(key)

    override fun setState(state: V) {
        glyph.getState()
                ?.toMutableMap()
                ?.apply { put(key, state) }
                ?.let(glyph::setState)
    }

}