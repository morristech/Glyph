package io.lamart.glyph.test

data class Person(val cars: List<Car> = listOf(
        Car("Ferrari"),
        Car("Bugatti"),
        Car("Donkervoort")
))
data class Car(
        val name: String,
        val steer: Steer = Steer(),
        val wheels: Map<Position, Wheel> = mapOf(
                Position.FRONT_LEFT to Wheel(),
                Position.FRONT_RIGHT to Wheel(),
                Position.REAR_LEFT to Wheel(),
                Position.REAR_RIGHT to Wheel()
        )
)
data class Horn(val isHonking: Boolean = true)
data class Steer(val horn: Horn = Horn())
data class Wheel(val size: Int = 15)
enum class Position {
    FRONT_LEFT,
    FRONT_RIGHT,
    REAR_LEFT,
    REAR_RIGHT
}
