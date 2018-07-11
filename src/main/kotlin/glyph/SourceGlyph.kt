package glyph

class SourceGlyph<T>(
        @Volatile private var state: T,
        observerCreator: ObserverCreator<T>? = null
) : Glyph<T> {

    private val observer: Observer<T> = observerCreator?.invoke(this) ?: {}

    override fun getState(): T = state

    override fun setState(state: T) {
        this.state = state
        observer(state)
    }

}

