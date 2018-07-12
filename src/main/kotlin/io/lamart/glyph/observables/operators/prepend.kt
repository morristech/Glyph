package io.lamart.glyph.observables.operators

import io.lamart.glyph.Observer
import io.lamart.glyph.observables.GlyphObservable
import io.lamart.glyph.observables.RemoveObserver

/**
 * Whenever you add an observer, it will directly receive the current state.
 */

fun <T> GlyphObservable<T>.prepend(): GlyphObservable<T> = PrependObservable(this)

private class PrependObservable<T>(private val observable: GlyphObservable<T>) : GlyphObservable<T> by observable {

    override fun addObserver(observer: Observer<T>): RemoveObserver {
        val removeObserver = observable.addObserver(observer)

        observable.glyph.getState().let(observer)
        return removeObserver
    }
}


