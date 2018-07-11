package glyph

interface Glyph<T>  {

    fun getState(): T

    fun setState(state: T)

}

fun <T> Glyph<T>.ifState(predicate: T.(T) -> Boolean, block: Glyph<T>.() -> Unit) {
    ifState(predicate)?.let { block(it) }
}

fun <T> Glyph<T>.ifState(predicate: T.(T) -> Boolean) =
        getState().let { state ->
            when {
                predicate(state, state) -> this
                else -> null
            }
        }