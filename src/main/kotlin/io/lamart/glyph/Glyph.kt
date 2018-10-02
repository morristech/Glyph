package io.lamart.glyph

import io.lamart.glyph.implementation.SimpleGlyph
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.operators.CastGlyph
import io.lamart.glyph.operators.ComposeGlyph
import io.lamart.glyph.operators.FilterGlyph
import io.lamart.glyph.operators.ToOptional
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> T.toGlyph(): Glyph<T> = Glyph(this)

interface Glyph<T> : ReadWriteProperty<Any, T> {

    val observable: Observable<T>

    fun get(): T

    fun set(state: T)

    fun set(transform: Transformer<T>)

    fun <R : T> cast(): Glyph<R> = CastGlyph(this)

    fun <R> compose(
            map: T.() -> R,
            reduce: T.(R) -> T
    ): Glyph<R> = ComposeGlyph(this, map, reduce)

    fun filter(predicate: T.(T) -> Boolean): OptionalGlyph<T> =
            FilterGlyph(this) { predicate(it, it) }

    fun toOptional(): OptionalGlyph<T> = ToOptional(this)

    override fun getValue(thisRef: Any, property: KProperty<*>): T = get()

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = set(value)

    companion object {

        operator fun <T> invoke(state: T): Glyph<T> = SimpleGlyph(state)

    }

}