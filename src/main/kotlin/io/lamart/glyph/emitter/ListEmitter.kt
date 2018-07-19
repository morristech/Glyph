package io.lamart.glyph.emitter

import io.lamart.glyph.implementation.Observer
import io.lamart.glyph.observable.RemoveObserver


class ListEmitter<T> : Emitter<T> {

    private val list = mutableListOf<Subscription>()

    override fun invoke(state: T) = list.forEach { observer -> observer(state) }

    override fun addObserver(observer: Observer<T>): RemoveObserver =
            Subscription(observer).also { list.add(it) }

    private inner class Subscription(observer: Observer<T>) : Observer<T> by observer, RemoveObserver {

        override fun invoke() {
            list.remove(this)
        }

    }
}
