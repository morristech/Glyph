package io.lamart.glyph

import io.lamart.glyph.observable.Observable
import io.lamart.glyph.operators.CastOptionalGlyph
import io.lamart.glyph.operators.ComposeOptionalGlyph
import io.lamart.glyph.operators.FilterOptionalGlyph
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface OptionalGlyph<T> : ReadWriteProperty<Any, T?> {

    val observable: Observable<T?>

    fun get(): T?

    fun set(state: T)

    fun transform(transformer: Transformer<T>)

    fun <R : T> cast(): OptionalGlyph<R> = CastOptionalGlyph(this)

    fun <R> compose(
            map: T.() -> R,
            reduce: T.(R) -> T
    ): OptionalGlyph<R> = ComposeOptionalGlyph(this, map, reduce)

    fun filter(predicate: T.(T) -> Boolean): OptionalGlyph<T> =
            FilterOptionalGlyph(this) { predicate(it, it) }

    override fun getValue(thisRef: Any, property: KProperty<*>): T? = get()

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        if (value != null)
            set(value)
    }

}