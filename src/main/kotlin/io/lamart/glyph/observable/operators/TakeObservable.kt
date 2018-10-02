package io.lamart.glyph.observable.operators

import io.lamart.glyph.Observer
import io.lamart.glyph.RemoveObserver
import io.lamart.glyph.observable.Observable
import java.util.concurrent.atomic.AtomicLong

internal class TakeObservable<T>(
        private val observable: Observable<T>,
        private val count: Long
) : Observable<T> {

    @Suppress("NAME_SHADOWING")
    override fun addObserver(observer: Observer<T>): RemoveObserver =
            TakeObserver(observer, count).let { observer ->
                observable.addObserver(observer).also { remove ->
                    observer.remove = remove
                }
            }

}

private class TakeObserver<T>(
        private val observer: Observer<T>,
        private val count: Long
) : Observer<T> {

    lateinit var remove: RemoveObserver
    val counter = AtomicLong()

    override fun invoke(item: T) {
        counter.getAndIncrement().also { currentCount ->
            when {
                currentCount < count -> observer(item)
                currentCount == count -> remove()
            }
        }
    }

}