package io.lamart.glyph.observable.operators

import io.lamart.glyph.Observer
import io.lamart.glyph.RemoveObserver
import io.lamart.glyph.observable.Observable


internal class LiftObservable<T, R>(
        private val observable: Observable<T>,
        private val lift: (Observer<R>) -> Observer<T>
) : Observable<R> {

    override fun addObserver(observer: Observer<R>): RemoveObserver =
            lift(observer).let(observable::addObserver)
}