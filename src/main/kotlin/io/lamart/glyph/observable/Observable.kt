/*
 * Copyright (c) 2018 Danny Lamarti.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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