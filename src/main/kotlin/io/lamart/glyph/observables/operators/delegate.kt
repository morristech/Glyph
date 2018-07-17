package io.lamart.glyph.observables.operators

import io.lamart.glyph.Observer
import io.lamart.glyph.Observable
import io.lamart.glyph.RemoveObserver

fun <T> Observable<T>.delegate(delegate: (T, Observer<T>) -> Unit): Observable<T> =
        DelegateObservable(this, delegate)

class DelegateObservable<T>(
        private val observable: Observable<T>,
        private val delegate: (T, Observer<T>) -> Unit
) : Observable<T> {

    override fun addObserver(observer: Observer<T>): RemoveObserver =
            observable.addObserver(TestObserver(observer))

    private inner class TestObserver(private val observer: Observer<T>) : Observer<T> {

        override fun invoke(state: T) = delegate(state, observer)

    }

}