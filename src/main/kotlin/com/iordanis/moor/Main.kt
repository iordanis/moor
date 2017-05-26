package com.iordanis.moor

fun main(args: Array<String>) {
    val createConnection = SqliteJdbc(args[0]).createConnection()
    val classFactory = ClassFactory(args[1])
    val metaData = createConnection.metaData
    val resultSetConverter = ResultSetConverter()

    val tables = resultSetConverter.convertTables(metaData.getTables(null, null, null, arrayOf("TABLE")))

    tables.forEach {
        println("Table: \t\t\t" + it)

        val columns = resultSetConverter.convertColumns(metaData.getColumns(null, null, it, null))
        println("Columns: \t\t" + columns.toString())

        val privateKeys = resultSetConverter.convertPrimaryKeys(metaData.getPrimaryKeys(null, null, it))
        println("Private Keys: \t" + privateKeys.toString())

        val indices = resultSetConverter.convertIndices(metaData.getIndexInfo(null, null, it, false, false))
        println("Indices: \t\t"+ indices.toString())

        classFactory.createClassFrom(Table(it))
    }
}

