package io.lamart.glyph.observable.operators

import io.lamart.glyph.implementation.Observer
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.observable.RemoveObserver

internal class FlatMapObservable<T, R>(
        private val observable: Observable<T>,
        private val delegate: (T, Observer<R>) -> Unit
) : Observable<R> {

    override fun addObserver(observer: Observer<R>): RemoveObserver =
            observable.addObserver(FlatMapObserver(observer, delegate))

    private inner class FlatMapObserver(
            private val observer: Observer<R>,
            private val delegate: (T, Observer<R>) -> Unit
    ) : Observer<T> by { state -> delegate(state, observer) }

}

