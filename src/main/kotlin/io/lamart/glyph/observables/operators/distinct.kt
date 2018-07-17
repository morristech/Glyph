package io.lamart.glyph.observables.operators

import io.lamart.glyph.Observer
import io.lamart.glyph.Observable
import io.lamart.glyph.RemoveObserver


fun <T> Observable<T>.distinct(): Observable<T> = DistinctObservable(this)

private class DistinctObservable<T>(private val observable: Observable<T>) : Observable<T> {

    override fun addObserver(observer: Observer<T>): RemoveObserver =
            observable.addObserver(DistinctObserver(observer))

    private inner class DistinctObserver(private val observer: Observer<T>) : Observer<T> {

        private var isInitialized = false
        private var state: T? = null

        override fun invoke(next: T) {
            if (isDistinct(next))
                observer(next)
        }

        private fun isDistinct(next: T): Boolean =
                synchronized(this) {
                    if (!isInitialized) {
                        state = next
                        isInitialized = true
                        true
                    } else if (state != next) {
                        state = next
                        true
                    } else {
                        false
                    }
                }

    }

}