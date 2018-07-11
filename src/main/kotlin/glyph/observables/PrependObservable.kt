package glyph.observables

import glyph.Glyph
import glyph.Observer

/**
 * Whenever you add an observer, it will directly receive the current state.
 */

fun <T> GlyphObservable<T>.prepend(): GlyphObservable<T> = PrependObservable(this)

private class PrependObservable<T>(private val observable: GlyphObservable<T>) : GlyphObservable<T> by observable {

    override fun addObserver(observer: Observer<T>): RemoveObserver {
        val removeObserver = observable.addObserver(observer)

        observer(observable.glyph.getState())
        return removeObserver
    }
}