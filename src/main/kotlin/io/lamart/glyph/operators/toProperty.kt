package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


internal class ToPropertyGlyph<T>(private val glyph: Glyph<T>) : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T = glyph.get()

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = glyph.set(value)

}

internal class ToPropertyOptionalGlyph<T>(private val glyph: OptionalGlyph<T>) : ReadWriteProperty<Any, T?> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T? = glyph.get()

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        if (value != null)
            glyph.set(value)
    }

}