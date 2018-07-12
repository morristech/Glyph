package io.lamart.glyph

interface Glyph<T> {

    fun getState(): T

    fun setState(state: T)

    @NotThreadSafe
    fun ifState(predicate: T.(T) -> Boolean) : Glyph<T>? =
            getState().takeIf { predicate(it, it) }?.let { this }

}