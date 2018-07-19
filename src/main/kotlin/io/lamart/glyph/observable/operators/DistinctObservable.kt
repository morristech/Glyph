package io.lamart.glyph.observable.operators

import io.lamart.glyph.implementation.Observer
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.observable.RemoveObserver



internal class DistinctObservable<T>(private val observable: Observable<T>) : Observable<T> {

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