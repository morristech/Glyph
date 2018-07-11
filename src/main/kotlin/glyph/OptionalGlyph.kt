package glyph

interface OptionalGlyph<T> {

    fun getState(): T?

    fun setState(state: T)

}

fun <T> OptionalGlyph<T>.ifState(predicate: T.(T) -> Boolean, block: OptionalGlyph<T>.() -> Unit) {
    ifState(predicate)?.let { block(it) }
}

fun <T> OptionalGlyph<T>.ifState(predicate: T.(T) -> Boolean) =
        getState()?.let { state ->
            when {
                predicate(state, state) -> this
                else -> null
            }
        }