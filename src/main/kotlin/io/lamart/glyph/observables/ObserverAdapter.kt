package io.lamart.glyph.observables

import io.lamart.glyph.ObserverCreator

interface ObserverAdapter<T> : ObserverCreator<T>, GlyphObservable<T>