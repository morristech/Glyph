package io.lamart.glyph.observable.operators

import io.lamart.glyph.Observer
import io.lamart.glyph.RemoveObserver
import io.lamart.glyph.observable.Observable

internal class FilterObservable<T>(
        private val observable: Observable<T>,
        private val predicate: (T) -> Boolean
) : Observable<T> {

    override fun addObserver(observer: Observer<T>): RemoveObserver =
            observable.addObserver(FilterObserver(observer))

    private inner class FilterObserver(private val observer: Observer<T>) : Observer<T> {

        override fun invoke(state: T) {
            state.takeIf(predicate)?.let(observer)
        }

    }

}