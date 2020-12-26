package org.task.manager.stub

object RandomGenerator {

    fun getString(length: Int = 36): String {
        val allowedChars = ('A'..'Z') + ('a'..'z')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

}