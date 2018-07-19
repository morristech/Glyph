package io.lamart.glyph.composition.door

sealed class Door(open val location: Location) {
    data class Open(override  val location: Location) : Door(location)
    data class Closed(override  val location: Location) : Door(location)
}