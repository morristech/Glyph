package io.lamart.glyph.observable

import io.lamart.glyph.Observer
import io.lamart.glyph.RemoveObserver
import io.lamart.glyph.observable.operators.*

interface Observable<T> {

    fun addObserver(observer: Observer<T>): RemoveObserver

    operator fun plus(observer: Observer<T>): RemoveObserver = addObserver(observer)

    operator fun Observer<T>.unaryPlus(): RemoveObserver = addObserver(this)

    fun <R : T> cast(): Observable<R> = CastObservable(this)

    /**
     * When the previous element is different from the next, it will emit next.
     */

    fun distinct(): Observable<T> =
            DistinctObservable(this)

    fun filter(predicate: (T) -> Boolean): Observable<T> =
            FilterObservable(this, predicate)

    fun <R> flatMap(delegate: (T) -> Iterable<R>): Observable<R> =
            FlatMapObservable(this, delegate)

    fun <R> intercept(wrap: (Observer<R>) -> Observer<T>): Observable<R> =
            InterceptObservable(this, wrap)

    fun <R> map(block: (T) -> R): Observable<R> =
            MapObservable(this, block)

    fun prepend(getState: () -> T): Observable<T> =
            PrependObservable(this, getState)

    fun take(count: Long): Observable<T> = TakeObservable(this, count)

    fun test(): TestObserver<T> = TestObserver<T>().also { addObserver(it) }

    fun test(block: TestObserver<T>.() -> Unit): RemoveObserver =
            TestObserver<T>().apply(block).let(::addObserver)

}