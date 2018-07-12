package io.lamart.glyph.observables

import io.lamart.glyph.Glyph
import io.lamart.glyph.Observer
import io.lamart.glyph.implementations.ObserverCreator


open class ObserverCreatorAdapter<T>(private val factory: (Glyph<T>) -> GlyphProcessor<T>) : ObserverCreator<T>, GlyphObservable<T> {

    private lateinit var observable: GlyphProcessor<T>
    override val glyph: Glyph<T>
        get() = observable.glyph

    override fun invoke(glyph: Glyph<T>): Observer<T> = factory(glyph).also { observable = it }

    override fun addObserver(observer: Observer<T>): RemoveObserver {
        try {
            return observable.addObserver(observer)
        } catch (e: UninitializedPropertyAccessException) {
            throw IllegalStateException("You can only add observers after creating the Glyph", e)
        }
    }

}