package io.lamart.glyph.observable.operators

import io.lamart.glyph.observable.Observer
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.observable.RemoveObserver

/**
 * Whenever you add an observer, it will directly receive the current state.
 */

internal class PrependObservable<T>(
        private val observable: Observable<T>,
        private val getState: () -> T
) : Observable<T> {

    override fun addObserver(observer: Observer<T>): RemoveObserver {
        val removeObserver = observable.addObserver(observer)

        getState().let(observer)
        return removeObserver
    }

}


