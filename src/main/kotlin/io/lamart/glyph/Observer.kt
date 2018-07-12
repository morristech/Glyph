package io.lamart.glyph

typealias Observer<T> = (T) -> Unit

/**
 * This function will only be called once
 */

typealias ObserverCreator<T> = (Glyph<T>) -> Observer<T>