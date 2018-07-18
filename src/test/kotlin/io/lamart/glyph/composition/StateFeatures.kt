package io.lamart.glyph.composition

import io.lamart.glyph.composition.house.HouseFeatures
import io.lamart.glyph.composition.user.UserFeatures
import io.lamart.glyph.observable.Observable

interface StateFeatures : UserFeatures, HouseFeatures {

    val observable: Observable<State>

}