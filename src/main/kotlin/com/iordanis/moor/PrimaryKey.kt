package com.iordanis.moor

/**
 * Holds metadata for a primary key belonging to a table.
 *
 * @param tableName the table where this primary key belongs to
 * @param columnName the column name relating to a primary key or a part of a primary key
 * @param keySequence the position of this column in the primary key
 */
data class PrimaryKey(val tableName: String, val columnName: String, val keySequence: Int)