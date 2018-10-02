/*
 * Copyright (c) 2018 Danny Lamarti.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.lamart.glyph.composition.user

import io.lamart.glyph.Glyph
import io.lamart.glyph.composition.NetworkModule

class UserInteractor(
        private val network: NetworkModule,
        glyph: Glyph<User>
) : UserFeatures, Glyph<User> by glyph {

    override fun login(name: String, pass: String) {
        if (get().isNotLoggedIn) {
            set(User.LoggingIn)
            authenticate(name, pass)
        }
    }

    private fun authenticate(name: String, pass: String) {
        network.authenticate(name, pass) { token, error ->
            if (get().isLoggingIn)
                set {
                    when {
                        error != null -> User.NotLoggedIn(error.message)
                        token != null -> User.LoggedIn(token)
                        else -> it
                    }
                }
        }
    }

    override fun logout() {
        if (get().isLoggedIn)
            set(User.NotLoggedIn())
    }

}