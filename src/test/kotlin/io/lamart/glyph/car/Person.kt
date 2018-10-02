/*
 * Copyright (c) 2018 Danny Lamarti.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.lamart.glyph.car

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
