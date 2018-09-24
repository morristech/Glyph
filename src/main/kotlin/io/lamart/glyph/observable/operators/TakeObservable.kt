package io.lamart.glyph.observable.operators

import io.lamart.glyph.Observer
import io.lamart.glyph.RemoveObserver
import io.lamart.glyph.observable.Observable
import java.util.concurrent.atomic.AtomicLong

internal class TakeObservable<T>(
        private val observable: Observable<T>,
        private val count: Long
) : Observable<T> {

    override fun addObserver(observer: Observer<T>): RemoveObserver {
        lateinit var remove: RemoveObserver
        val counter = AtomicLong()
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