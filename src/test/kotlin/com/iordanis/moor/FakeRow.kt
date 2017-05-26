package com.iordanis.moor

class FakeRow {
    private val columnData: MutableMap<String, String> = HashMap()

    fun addColumn(key: String, value: String): com.iordanis.moor.FakeRow {
        columnData.put(key, value)
        return this
    }

    fun getString(key: String): String? {
        return columnData[key]
    }
}