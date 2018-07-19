package io.lamart.glyph.observable.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.implementation.Observer
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.observable.RemoveObserver

/**
 * Whenever you add an observer, it will directly receive the current state.
 */

internal class PrependObservable<T>(
        private val glyph: Glyph<T>,
        private val observable: Observable<T>
) : Observable<T> {

    override fun addObserver(observer: Observer<T>): RemoveObserver {
        val removeObserver = observable.addObserver(observer)

        glyph.get().let(observer)
        return removeObserver
    }
}


