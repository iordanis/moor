package com.iordanis.moor

import java.util.*

data class Table(val name: String) {
    private val separatingRegex = Regex("_.")

    fun prettifiedTableName(): String {
        return name
                .capitalize()
                .replace(separatingRegex, { match -> match.value.substring(1).toUpperCase(Locale.ENGLISH) })
    }

    fun hasTableNameBeenPrettified(): Boolean {
        return name.contains(separatingRegex)
    }
}
