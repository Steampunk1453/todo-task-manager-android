package org.task.manager.presentation.audiovisual

enum class Type(val value: String) {

    MOVIE("Movie"),
    TV_SHOW("TV Show");

    companion object {
        fun fromString(type: String): Type {
            return values().firstOrNull { it.value == type }
                ?: throw IllegalArgumentException("Not type found for: $type")
        }
    }

}