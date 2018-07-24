package io.lamart.glyph.observable.emitter

import io.lamart.glyph.implementation.Observer
import io.lamart.glyph.observable.RemoveObserver


class SynchronizedListEmitter<T> : Emitter<T> {

    private val lock: Any = Any()
    private val list = mutableListOf<Subscription>()

    override fun invoke(state: T) =
            synchronized(lock, { list.toList() }).forEach { observer -> observer(state) }

    override fun addObserver(observer: Observer<T>): RemoveObserver {
        val subscription = Subscription(observer)

        synchronized(lock) { list.add(subscription) }
        return subscription
    }

    private inner class Subscription(observer: Observer<T>) : Observer<T> by observer, RemoveObserver {

        override fun invoke() {
            synchronized(lock) { list.remove(this) }
        }

    }
}
