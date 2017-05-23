package com.iordanis.moor

import java.sql.Connection
import java.sql.DriverManager

class SqliteJdbc(val path: String) {

    fun createConnection(): Connection {
        val jdbcUrl = "jdbc:sqlite:" + path
        return DriverManager.getConnection(jdbcUrl)
    }
}
