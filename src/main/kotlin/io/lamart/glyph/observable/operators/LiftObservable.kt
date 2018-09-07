package io.lamart.glyph.observable.operators

import io.lamart.glyph.observable.Observer
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.observable.RemoveObserver


internal class LiftObservable<T, R>(
        private val observable: Observable<T>,
        private val lift: (Observer<R>) -> Observer<T>
) : Observable<R> {

    override fun addObserver(observer: Observer<R>): RemoveObserver =
            lift(observer).let(observable::addObserver)
}