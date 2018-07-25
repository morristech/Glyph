package io.lamart.glyph.observable.operators

import io.lamart.glyph.implementation.Observer
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.observable.RemoveObserver

internal class FlatMapIterableObservable<T, R>(
        private val observable: Observable<T>,
        private val delegate: (T) -> Iterable<R>
) : Observable<R> {

    override fun addObserver(observer: Observer<R>): RemoveObserver =
            observable.addObserver(FlatMapIterableObserver(observer, delegate))

    private inner class FlatMapIterableObserver(
            private val observer: Observer<R>,
            private val delegate: (T) -> Iterable<R>
    ) : Observer<T> by { state -> delegate(state).forEach(observer) }

}