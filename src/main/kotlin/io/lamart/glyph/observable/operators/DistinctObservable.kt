package io.lamart.glyph.observable.operators

import io.lamart.glyph.Observer
import io.lamart.glyph.RemoveObserver
import io.lamart.glyph.observable.Observable


internal class DistinctObservable<T>(private val observable: Observable<T>) : Observable<T> {

    override fun addObserver(observer: Observer<T>): RemoveObserver =
            observable.addObserver(DistinctObserver(observer))

    private inner class DistinctObserver(private val observer: Observer<T>) : Observer<T> {

        private val lock = Any()
        private var isInitialized = false
        private var state: T? = null

        override fun invoke(next: T) =
                updateDistinct(next).let { isDistinct ->
                    if (isDistinct)
                        observer(next)
                }

        private fun updateDistinct(next: T): Boolean =
                synchronized(lock) {
                    when {
                        !isInitialized -> {
                            state = next
                            isInitialized = true
                            true
                        }
                        state != next -> {
                            state = next
                            true
                        }
                        else -> false
                    }
                }

    }

}