package com.nicchongwb.plugins

import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.util.render
import org.jooq.Allow
fun getAllowPlainSQLAnnotation(element: IrElement): String {
  val annotations = when (element) {
    is IrClass -> element.annotations
    is IrSimpleFunction -> element.annotations
    else -> return ""
  }

  return annotations.find {
    it.type.render() == Allow.PlainSQL::class.qualifiedName
  }?.type?.render()?.toString() ?: ""
}

fun isAllowPlainSQLAnnotation(data: String): Boolean {
  if (data == Allow.PlainSQL::class.qualifiedName) {
    return true
  }
  return false
}

// similar to Tools.checkPlainSQL https://github.com/jOOQ/jOOQ/blob/main/jOOQ-checker/src/main/java/org/jooq/checker/Tools.java
fun checkPlainSQL() {}
