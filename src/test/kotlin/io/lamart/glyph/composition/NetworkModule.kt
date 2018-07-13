package io.lamart.glyph.composition

interface NetworkModule {

    fun authenticate(name: String, pass: String, callback: (String?, Throwable?) -> Unit)

    object Instance : NetworkModule {
        override fun authenticate(name: String, pass: String, callback: (String?, Throwable?) -> Unit) {
            if (name.isEmpty() or pass.isEmpty())
                callback(null, Throwable("Invalid credentials"))
            else
                callback("myPrettyToken", null)
        }

    }

}