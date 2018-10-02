/*
 * Copyright (c) 2018 Danny Lamarti.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.lamart.glyph.observable.operators

import io.lamart.glyph.Observer

class TestObserver<T> internal constructor() : Observer<T> {

    private val list = mutableListOf<T>()
    private val observers = mutableListOf<DoneObserver<T>>()

    override fun invoke(state: T) {
        list.add(state)

        observers.iterator().let { iterator ->
            while (iterator.hasNext()) {
                iterator.next().run {
                    invoke(state)

                    if (done)
                        iterator.remove()
                }
            }
        }
    }

    fun valueAt(index: Int, assert: T.(T) -> Unit): TestObserver<T> = apply {
        ValueAtObserver(index, assert).let(::addIfNotDone)
    }

    fun assertValueAt(index: Int, assert: T.(T) -> Boolean): TestObserver<T> = apply {
        AssertValueAtObserver(index, assert).let(::addIfNotDone)
    }

    private fun addIfNotDone(observer: DoneObserver<T>) {
        if (list.isNotEmpty())
            observer(list.last())

        if (!observer.done)
            observers.add(observer)
    }

    private interface DoneObserver<T> : Observer<T> {
        val done: Boolean
    }

    private inner class ValueAtObserver(
            private val index: Int,
            private val assert: T.(T) -> Unit
    ) : DoneObserver<T> {

        override var done: Boolean = false
            private set

        override fun invoke(state: T) {
            if (!done && index < list.size) {
                list[index].let { element ->
                    if (element !== state)
                        throw AssertionError("Element[$index] $element is not equal check $state")

                    assert(element, element)
                }

                done = true
            }
        }

    }

    private inner class AssertValueAtObserver(
            private val index: Int,
            private val assert: T.(T) -> Boolean
    ) : DoneObserver<T> {

        override var done: Boolean = false
            private set

        override fun invoke(state: T) {
            if (!done && index < list.size) {
                list[index].let { element ->
                    if (element !== state)
                        throw AssertionError("Element[$index] $element is not equal check $state")

                    if (!assert(element, element))
                        throw AssertionError("Error at index=$index")
                }

                done = true
            }
        }

    }


}