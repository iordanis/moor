package com.iordanis.moor

import java.sql.ResultSet

class ResultSetConverter {

    /**
     * The JDBC spec defines the following columns as table metadata: [java.sql.DatabaseMetaData.getTables]
     */
    fun convertTables(tablesResultSet: ResultSet): List<String> {
        val list = ArrayList<String>()
        while (tablesResultSet.next()) {
            val tableName = tablesResultSet.getString("TABLE_NAME")
            if (tableName != null) {
                list.add(tableName)
            }
        }
        return list.filter { it != "android_metadata" && it != "sqlite_sequence" }
    }

    /**
     * The JDBC spec defines the following columns as column metadata: [java.sql.DatabaseMetaData.getColumns]
     */
    fun convertColumns(columns: ResultSet): List<Column> {
        val list = ArrayList<Column>()
        while (columns.next()) {
            val tableName = columns.getString("TABLE_NAME")
            val columnName = columns.getString("COLUMN_NAME")
            val dataType = columns.getInt("DATA_TYPE")
            list.add(Column(tableName, columnName, dataType))
        }
        return list
    }

    /**
     * The JDBC spec defines the following columns as primary key metadata: [java.sql.DatabaseMetaData.getPrimaryKeys]
     */
    fun convertPrimaryKeys(primaryKeys: ResultSet): List<PrimaryKey> {
        val list = ArrayList<PrimaryKey>()
        while (primaryKeys.next()) {
            val tableName = primaryKeys.getString("TABLE_NAME")
            val columnName = primaryKeys.getString("COLUMN_NAME")
            val keySequence = primaryKeys.getInt("KEY_SEQ")
            list.add(PrimaryKey(tableName, columnName, keySequence))
        }
        return list
    }

    /**
     * The JDBC spec defines the following columns as index information: [java.sql.DatabaseMetaData.getIndexInfo]
     */
    fun convertIndices(indices: ResultSet): List<Index> {
        val list = ArrayList<Index>()
        while (indices.next()) {
            val tableName = indices.getString("TABLE_NAME")
            val indexName = indices.getString("INDEX_NAME")
            val nonUnique = indices.getBoolean("NON_UNIQUE")
            val columnName = indices.getString("COLUMN_NAME")
            val position = indices.getInt("ORDINAL_POSITION")
            list.add(Index(tableName, indexName, nonUnique, columnName, position))
        }
        return list
    }
}

