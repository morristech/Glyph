package io.lamart.glyph.composition

import io.lamart.glyph.composition.user.User

data class State(val user: User = User.NotLoggedIn())