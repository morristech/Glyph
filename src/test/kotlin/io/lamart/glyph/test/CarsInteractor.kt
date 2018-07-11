package io.lamart.glyph.test

import glyph.Glyph
import glyph.composers.compose
import glyph.composers.composeList

class CarsInteractor(private val glyph: Glyph<List<Car>>) {

    fun startHonking(carName: String) = getHornInteractor(carName).startHonking()

    fun stopHonking(carName: String) = getHornInteractor(carName).stopHonking()

    private fun getHornInteractor(carName: String): HornInteractor =
            glyph.composeList { car -> car.name == carName }
                    .compose({ steer }, { copy(steer = it) })
                    .compose({ horn }, { copy(horn = it) })
                    .let(::HornInteractor)

}