package glyph

fun <T> Glyph<T>.toOptional(): OptionalGlyph<T> = ToOptionalGlyph(this)

class ToOptionalGlyph<T>(private val glyph: Glyph<T>) : OptionalGlyph<T> {

    override fun getState(): T? = glyph.getState()

    override fun setState(state: T) = glyph.setState(state)

}