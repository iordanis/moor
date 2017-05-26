package com.iordanis.moor

/**
 * Holds metadata for an index of a table.
 *
 * @param tableName the table name
 * @param indexName the index name
 * @param nonUnique whether the index allows non-unique values
 * @param columnName the column name that forms the index or part of the index
 * @param position the position of this column in the index
 */
data class Index(val tableName: String, val indexName: String, val nonUnique: Boolean, val columnName: String,
                 val position: Int)
