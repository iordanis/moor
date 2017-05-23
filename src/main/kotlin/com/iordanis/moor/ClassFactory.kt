package com.iordanis.moor

import android.arch.persistence.room.Entity
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

class ClassFactory(val appPackage: String) {

    fun createClassFrom(table: Table) {
        val tableClassBuilder = TypeSpec.classBuilder(table.prettifiedTableName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        if (table.hasTableNameBeenPrettified()) {
            tableClassBuilder.addAnnotation(AnnotationSpec.builder(Entity::class.java)
                    .addMember("tableName", "\$S", table.name)
                    .build())
        } else {
            tableClassBuilder.addAnnotation(Entity::class.java)
        }
        val javaFile = JavaFile.builder(appPackage + ".db", tableClassBuilder.build())
                .build()
        javaFile.writeTo(System.out)
        println()
        println()
        println()
    }
}