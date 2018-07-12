package io.lamart.glyph

interface OptionalGlyph<T> {

    fun getState(): T?

    fun setState(state: T)

    @NotThreadSafe
    fun ifState(predicate: T.(T) -> Boolean): OptionalGlyph<T>? =
            getState()?.takeIf { predicate(it, it) }?.let { this }

}