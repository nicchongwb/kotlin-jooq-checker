package com.nicchongwb.plugins

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.SourceRangeInfo
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.util.dump
import org.jetbrains.kotlin.ir.util.render
import org.jooq.Allow
import org.jooq.PlainSQL

fun getAllowPlainSqlAnnotation(element: IrElement): String {
  val annotations = when (element) {
    is IrClass -> element.annotations
    is IrSimpleFunction -> element.annotations
    else -> return ""
  }

  return annotations.find {
    it.type.render() == Allow.PlainSQL::class.qualifiedName
  }?.type?.render()?.toString() ?: ""
}

fun isAllowPlainSqlAnnotation(data: String): Boolean {
  return data == Allow.PlainSQL::class.qualifiedName
}

fun isPlainSqlAnnotation(data: String): Boolean {
  return data == PlainSQL::class.qualifiedName
}

fun debugIrElement(element: IrElement) {
  when (element) {
    is IrClass, is IrSimpleFunction, is IrCall -> println(element.dump())
  }
}
fun debugCheckPlainSql(severity: CompilerMessageSeverity, err: String, compilerMsgLocation: CompilerMessageLocation) {
  println("${severity.name}: $err")
  compilerMsgLocation.let {
    println("At ${it.path}:${it.line}:${it.column}")
  }
}

// similar to Tools.checkPlainSQL https://github.com/jOOQ/jOOQ/blob/main/jOOQ-checker/src/main/java/org/jooq/checker/Tools.java
const val notAllowedErrorMsg = "error: [Plain SQL usage not allowed at current scope. Use @Allow.PlainSQL.]"
fun checkPlainSQL(element: IrElement, data: IrContext, mc: MessageCollector) {
  when (element) {
    is IrCall -> {
      val innerIrSimpleFunc = element.symbol.owner as? IrSimpleFunction

      if (innerIrSimpleFunc != null) {
        data.plainSqlAnnotation = getAllowPlainSqlAnnotation(innerIrSimpleFunc)

        if (isPlainSqlAnnotation(data.plainSqlAnnotation)) {
          // if @PlainSQL exist, check if @Allow.PlainSQL is present in current/parent nodes
          if (!isAllowPlainSqlAnnotation(data.allowPlainSqlAnnotation)) {
            // Report compile error info

            val elementSrcRangeInfo = data.srcFile!!.getSourceRangeInfo(element) // element's info on SrcFile range
            val elementSrcText = data.srcFile!!.getText(elementSrcRangeInfo) // element's text in SrcFile
            val elementCompilerMsgLocation = data.srcFile!!.getCompilerMessageLocation(element)

            val err = "$notAllowedErrorMsg $elementSrcText"
            if (data.debugAST) debugCheckPlainSql(CompilerMessageSeverity.ERROR, err, elementCompilerMsgLocation)
            mc.report(CompilerMessageSeverity.ERROR, err, elementCompilerMsgLocation)
          }
        }
      }
    }
  }
}
