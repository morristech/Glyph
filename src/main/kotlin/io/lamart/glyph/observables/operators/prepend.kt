package io.lamart.glyph.observables.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.Observer
import io.lamart.glyph.observables.Observable
import io.lamart.glyph.observables.RemoveObserver

/**
 * Whenever you add an observer, it will directly receive the current state.
 */

fun <T> Observable<T>.prepend(glyph: Glyph<T>): Observable<T> = PrependObservable(glyph, this)

private class PrependObservable<T>(
        private val glyph: Glyph<T>,
        private val observable: Observable<T>
) : Observable<T> {

    override fun addObserver(observer: Observer<T>): RemoveObserver {
        val removeObserver = observable.addObserver(observer)

        glyph.getState().let(observer)
        return removeObserver
    }
}


