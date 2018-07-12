package io.lamart.glyph.observables

import io.lamart.glyph.implementations.ObserverCreator

interface ObserverAdapter<T> : ObserverCreator<T>, GlyphObservable<T>