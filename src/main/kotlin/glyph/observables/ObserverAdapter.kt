package glyph.observables

import glyph.ObserverCreator

interface ObserverAdapter<T> : ObserverCreator<T>, GlyphObservable<T>