package com.iordanis.moor

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.junit.Test as test

class ResultSetConverterTest {
    private val TABLE_NAME = "TABLE_NAME"
    private val COLUMN_NAME = "COLUMN_NAME"
    private val DATA_TYPE = "DATA_TYPE"
    private val KEY_SEQ = "KEY_SEQ"
    private val INDEX_NAME = "INDEX_NAME"
    private val NON_UNIQUE = "NON_UNIQUE"
    private val ORDINAL_POSITION = "ORDINAL_POSITION"

    private val USER = "user"
    private val FIRST_NAME = "first_name"
    private val LAST_NAME = "last_name"

    private val resultSetConverter = ResultSetConverter()

    @test fun retrievesTableNamesFromTableResultSet() {
        val address = "address"
        val fakeResultSet = FakeResultSet()
                .addRow(table(name = USER))
                .addRow(table(name = address))

        assertThat(resultSetConverter.convertTables(fakeResultSet), contains(USER, address))
    }

    @test fun skipsAndroidTables() {
        val tablesResultSet = FakeResultSet()
                .addRow(table(name = USER))
                .addRow(table(name = "android_metadata"))
                .addRow(table(name = "sqlite_sequence"))

        assertThat(resultSetConverter.convertTables(tablesResultSet), contains(USER))
    }


    @test fun retrievesColumnDetailsFromColumnResultSet() {
        val uid = "uid"
        val integer = 4
        val varChar = 12

        val columnsResultSet = FakeResultSet()
                .addRow(column(name = uid, dataType = integer))
                .addRow(column(name = FIRST_NAME, dataType = varChar))
                .addRow(column(name = LAST_NAME, dataType = varChar))

        assertThat(resultSetConverter.convertColumns(columnsResultSet), contains(
                Column(USER, uid, integer),
                Column(USER, FIRST_NAME, varChar),
                Column(USER, LAST_NAME, varChar)
        ))
    }

    @test fun retrievesPrimaryKeysFromResultSet() {
        val primaryKeysResultSet = FakeResultSet()
                .addRow(primaryKey(columnName = FIRST_NAME, keySequence = 0))
                .addRow(primaryKey(columnName = LAST_NAME, keySequence = 1))

        assertThat(resultSetConverter.convertPrimaryKeys(primaryKeysResultSet), contains(
                PrimaryKey(USER, FIRST_NAME, 0),
                PrimaryKey(USER, LAST_NAME, 1)
        ))
    }

    @test fun retrievesIndicesFromResultSet() {
        val userNameIndex = "user_name_idx"
        val indicesResultSet = FakeResultSet()
                .addRow(index(name = userNameIndex, nonUnique = true, columnName = LAST_NAME, position = 1))
                .addRow(index(name = userNameIndex, nonUnique = true, columnName = FIRST_NAME, position = 2))

        assertThat(resultSetConverter.convertIndices(indicesResultSet), contains(
                Index(USER, userNameIndex, true, LAST_NAME, 1),
                Index(USER, userNameIndex, true, FIRST_NAME, 2)
        ))
    }

    private fun table(name: String) = FakeRow().addColumn(TABLE_NAME, name)

    private fun column(name: String, dataType: Int) = FakeRow()
            .addColumn(TABLE_NAME, USER)
            .addColumn(COLUMN_NAME, name)
            .addColumn(DATA_TYPE, dataType.toString())

    private fun primaryKey(columnName: String, keySequence: Int) = FakeRow()
            .addColumn(TABLE_NAME, USER)
            .addColumn(COLUMN_NAME, columnName)
            .addColumn(KEY_SEQ, keySequence.toString())

    private fun index(name: String, nonUnique: Boolean, columnName: String, position: Int): FakeRow {
        return FakeRow()
                .addColumn(TABLE_NAME, USER)
                .addColumn(INDEX_NAME, name)
                .addColumn(NON_UNIQUE, nonUnique.toString())
                .addColumn(COLUMN_NAME, columnName)
                .addColumn(ORDINAL_POSITION, position.toString())
    }
}


