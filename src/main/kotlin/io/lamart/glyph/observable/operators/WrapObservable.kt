package io.lamart.glyph.observable.operators

import io.lamart.glyph.implementation.Observer
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.observable.RemoveObserver


internal class WrapObservable<T>(
        private val observable: Observable<T>,
        private val wrap: (Observer<T>) -> Observer<T>
) : Observable<T> {

    override fun addObserver(observer: Observer<T>): RemoveObserver =
            wrap(observer).let(observable::addObserver)
}