package glyph

fun <T> Glyph<T>.intercept(
        getState: Glyph<T>.() -> T = { getState() },
        setState: Glyph<T>.(T) -> Unit = { it -> setState(it) }
): Glyph<T> = Interceptor(this, getState, setState)

class Interceptor<T>(
        private val glyph: Glyph<T>,
        private val getState: Glyph<T>.() -> T,
        private val setState: Glyph<T>.(T) -> Unit
) : Glyph<T> {

    override fun getState(): T = getState(glyph)

    override fun setState(state: T) = setState(glyph, state)

}