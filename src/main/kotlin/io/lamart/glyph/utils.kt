package io.lamart.glyph


typealias Transformer<T> = T.(T) -> T

typealias Observer<T> = (T) -> Unit

typealias RemoveObserver = () -> Unit