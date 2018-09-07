package io.lamart.glyph.observable

interface ObservableSource<T> {

    fun addObserver(observer: Observer<T>): RemoveObserver

    operator fun plus(observer: Observer<T>): RemoveObserver = addObserver(observer)

    operator fun Observer<T>.unaryPlus(): RemoveObserver = addObserver(this)

}