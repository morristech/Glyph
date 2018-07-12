package io.lamart.glyph.observables

import io.lamart.glyph.Observer

interface GlyphProcessor<T> : GlyphObservable<T>, Observer<T>