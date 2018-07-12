package glyph

interface OptionalGlyph<T> {

    fun getState(): T?

    fun setState(state: T)

    fun ifState(predicate: T.(T) -> Boolean) =
            getState()?.takeIf { predicate(it, it) }?.let { this }

}