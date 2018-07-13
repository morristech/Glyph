package io.lamart.glyph.composition.user

import io.lamart.glyph.Glyph
import io.lamart.glyph.composition.NetworkModule

class UserInteractor(
        private val network: NetworkModule,
        glyph: Glyph<User>
) : UserFeatures, Glyph<User> by glyph {

    override fun login(name: String, pass: String) {
        if (getState().isNotLoggedIn) {
            setState(User.LoggingIn)
            authenticate(name, pass)
        }
    }

    private fun authenticate(name: String, pass: String) {
        network.authenticate(name, pass) { token, error ->
            setStateIf({ isLoggingIn }, {
                when {
                    error != null -> User.NotLoggedIn(error.message)
                    token != null -> User.LoggedIn(token)
                    else -> it
                }
            })
        }
    }

    override fun logout() {
        setStateIf({ isLoggedIn }, { User.NotLoggedIn() })
    }

}