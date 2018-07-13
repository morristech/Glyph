package io.lamart.glyph.observables.operators

import io.lamart.glyph.Observer
import io.lamart.glyph.observables.Observable
import io.lamart.glyph.observables.RemoveObserver

fun <T> Observable<T>.test(): TestObserver<T> = TestObserver<T>().also { addObserver(it) }

fun <T> Observable<T>.test(block: TestObserver<T>.() -> Unit): RemoveObserver =
        TestObserver<T>().apply(block).let(::addObserver)

class TestObserver<T> : Observer<T> {

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
                        throw AssertionError("Element[$index] $element is not equal to $state")

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
                        throw AssertionError("Element[$index] $element is not equal to $state")

                    if (!assert(element, element))
                        throw AssertionError("Error at index=$index")
                }

                done = true
            }
        }

    }


}