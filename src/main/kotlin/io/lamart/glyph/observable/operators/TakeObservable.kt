package io.lamart.glyph.observable.operators

import io.lamart.glyph.observable.Observer
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.observable.RemoveObserver
import java.util.concurrent.atomic.AtomicLong

internal class TakeObservable<T>(
        private val observable: Observable<T>,
        private val count: Long
) : Observable<T> {

    private val counter = AtomicLong()

    override fun addObserver(observer: Observer<T>): RemoveObserver {
        lateinit var remove: RemoveObserver
        val result = observable.addObserver { item ->
            counter.getAndIncrement().also { currentCount ->
                when {
                    currentCount < count -> observer(item)
                    currentCount == count -> remove.invoke()
                }
            }
        }

        remove = result
        return result
    }

}