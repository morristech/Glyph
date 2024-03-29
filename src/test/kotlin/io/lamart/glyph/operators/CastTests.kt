/*
 * Copyright (c) 2018 Danny Lamarti.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.lamart.glyph.operators

import io.lamart.glyph.SimpleGlyph
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class CastTests {

    sealed class User {
        object NotLoggedIn : User()
        data class LoggedIn(val name: String) : User()
    }

    @Test
    fun test() {
        val glyph = SimpleGlyph<User>(User.NotLoggedIn)
        val loggedInGlyph = glyph
                .filter { it is User.LoggedIn }
                .cast<User.LoggedIn>()

        loggedInGlyph.get().let(::assertNull)
        loggedInGlyph.set(User.LoggedIn("Danny"))
        loggedInGlyph.get().let(::assertNotNull)
        glyph.set(User.NotLoggedIn)
        loggedInGlyph.get().let(::assertNull)
    }

}