package io.lamart.glyph.observables.operators

import io.lamart.glyph.Observer
import io.lamart.glyph.observables.Observable
import io.lamart.glyph.observables.RemoveObserver

fun <T> Observable<T>.filter(predicate: (T) -> Boolean): Observable<T> = FilterObservable(this, predicate)

class FilterObservable<T>(
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