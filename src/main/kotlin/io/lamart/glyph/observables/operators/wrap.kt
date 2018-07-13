package io.lamart.glyph.observables.operators

import io.lamart.glyph.Observer
import io.lamart.glyph.observables.Observable
import io.lamart.glyph.observables.RemoveObserver

fun <T> Observable<T>.wrap(wrap: (Observer<T>) -> Observer<T>): Observable<T> =
        WrapObservable(this, wrap)

private class WrapObservable<T>(
        private val observable: Observable<T>,
        private val wrap: (Observer<T>) -> Observer<T>
) : Observable<T> {

    override fun addObserver(observer: Observer<T>): RemoveObserver =
            wrap(observer).let(observable::addObserver)
}