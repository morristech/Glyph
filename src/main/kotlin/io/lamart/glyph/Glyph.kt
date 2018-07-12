package io.lamart.glyph

interface Glyph<T> {

    fun getState(): T

    fun setState(state: T)

    @NotThreadSafe
    fun ifState(predicate: T.(T) -> Boolean): Glyph<T>? =
            getState().takeIf { predicate(it, it) }?.let { this }

    @ThreadSafe
    fun ifState(predicate: T.(T) -> Boolean, block: Glyph<T>.() -> Unit) =
            getState().takeIf { predicate(it, it) }?.let { block(this) }

}