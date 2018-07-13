package io.lamart.glyph.composition.user

import io.lamart.glyph.composition.house.House


sealed class User {

    data class NotLoggedIn(val reason: String? = null) : User()
    object LoggingIn : User()
    data class LoggedIn(val token: String, val house: House = House()) : User()

    val isNotLoggedIn: Boolean by lazy { this is NotLoggedIn }
    val isLoggingIn: Boolean by lazy { this === LoggingIn }
    val isLoggedIn: Boolean by lazy { this is LoggedIn }

}


