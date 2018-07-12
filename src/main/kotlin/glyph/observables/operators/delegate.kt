package glyph.observables.operators

import glyph.Observer
import glyph.observables.Observable
import glyph.observables.RemoveObserver

fun <T> Observable<T>.delegate(delegate: Observer<T>.(T) -> Unit): Observable<T> =
        DelegateObservable(this, delegate)

class DelegateObservable<T>(
        private val observable: Observable<T>,
        private val delegate: Observer<T>.(T) -> Unit
) : Observable<T> {

    override fun addObserver(observer: Observer<T>): RemoveObserver =
            observable.addObserver(TestObserver(observer))

    private inner class TestObserver(private val observer: Observer<T>) : Observer<T> {

        override fun invoke(state: T) = delegate(observer, state)

    }

}