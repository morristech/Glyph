package io.lamart.glyph.observable.operators

import io.lamart.glyph.Observer
import io.lamart.glyph.RemoveObserver
import io.lamart.glyph.observable.Observable

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


