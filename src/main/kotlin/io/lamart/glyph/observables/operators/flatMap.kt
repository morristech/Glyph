package io.lamart.glyph.observables.operators

import io.lamart.glyph.Observable
import io.lamart.glyph.Observer
import io.lamart.glyph.RemoveObserver

fun <T, R> Observable<T>.flatMap(block: (T) -> Observable<R>): Observable<R> =
        FlatMapObservable(this, block)

@JvmName("flatMapIterable")
fun <T, R> Observable<T>.flatMap(block: (T) -> Iterable<R>): Observable<R> =
        IteratorObservableFactory
                .fromIterable(block)
                .let { FlatMapObservable(this, it) }

@JvmName("flatMapSequence")
fun <T, R> Observable<T>.flatMap(block: (T) -> Sequence<R>): Observable<R> =
        IteratorObservableFactory
                .fromSequence(block)
                .let { FlatMapObservable(this, it) }

private class FlatMapObservable<T, R>(
        private val observable: Observable<T>,
        private val flatMap: (T) -> Observable<R>
) : Observable<R> {

    private val lock = Any()
    private val removeObservers = mutableListOf<RemoveObserver>()

    override fun addObserver(observer: Observer<R>): RemoveObserver {
        val removeObserver = observable.addObserver { state ->
            flatMap(state)
                    .addObserver(observer)
                    .let { addToRemoveObservers(it) }
        }

        return {
            removeObserver()
            clearRemoveObservers()
        }
    }

    private fun addToRemoveObservers(removeObserver: RemoveObserver) =
            synchronized(lock, { removeObservers.add(removeObserver) })

    private fun clearRemoveObservers() {
        synchronized(lock, {
            val list = removeObservers.toList()

            removeObservers.clear()
            list
        }).forEach { removeObserver -> removeObserver() }
    }

}

private class IteratorObservableFactory<T, R>(private val iteratorFactory: (T) -> Iterator<R>) : (T) -> Observable<R> {

    override fun invoke(state: T): Observable<R> = iteratorFactory(state).let(::IteratorObservable)

    companion object {

        fun <T, R> fromIterable(block: (T) -> Iterable<R>): IteratorObservableFactory<T, R> =
                IteratorObservableFactory({ block(it).iterator() })

        fun <T, R> fromSequence(block: (T) -> Sequence<R>): IteratorObservableFactory<T, R> =
                IteratorObservableFactory({ block(it).iterator() })

    }

}

private class IteratorObservable<T>(private val iterator: Iterator<T>) : Observable<T> {

    @Volatile
    private var cancelled: Boolean = false

    override fun addObserver(observer: Observer<T>): RemoveObserver {
        iterator.run {
            while (!cancelled and hasNext())
                next().let(observer)
        }

        return { cancelled = true }
    }

}
