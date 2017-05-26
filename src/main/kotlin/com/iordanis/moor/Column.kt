package com.iordanis.moor

/**
 * Holds metadata for a table column.
 *
 * @param tableName the table name
 * @param columnName the column name
 * @param dataType data type as per [java.sql.Types]
 */
data class Column(val tableName: String, val columnName: String, val dataType: Int)
