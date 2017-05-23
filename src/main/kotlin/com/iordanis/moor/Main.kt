package com.iordanis.moor

fun main(args: Array<String>) {
    val createConnection = SqliteJdbc(args[0]).createConnection()
    val resultSet = createConnection.metaData.getTables(null, null, null, arrayOf("TABLE"))
    val classFactory = ClassFactory(args[1])
    while (resultSet.next()) {
        val tableName = resultSet.getString("TABLE_NAME")
        if (tableName == "android_metadata" || tableName == "sqlite_sequence") {
            continue
        }
        classFactory.createClassFrom(Table(tableName))
    }
}
