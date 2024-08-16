package com.nicchongwb.plugins

import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor
import kotlin.math.exp

/*
Tree visitor pattern to traverse the IR tree
- during visitation, check for @Allow.PlainSQL & @PlainSQL annotation depending on the IrElement
- take note if parent node has @Allow.PlainSQL by passing state of data to child node
 */

class PlainSQLCheckerVisitor(val mc: MessageCollector): IrElementVisitor<Unit, IrContext> {
  override fun visitElement(element: IrElement, data: IrContext) {
    element.acceptChildren(this, data) // acceptChildren for recursive visitation
  }

  override fun visitFile(declaration: IrFile, data: IrContext) {
    data.irFile = declaration
    data.srcFile = SourceFile(declaration)
    super.visitFile(declaration, data)
  }

  override fun visitClass(declaration: IrClass, data: IrContext) {
    // TODO
    //  - debugAST
    data.allowPlainSqlAnnotation = getAllowPlainSqlAnnotation(declaration)
    super.visitClass(declaration, data)
  }

  override fun visitSimpleFunction(declaration: IrSimpleFunction, data: IrContext) {
    // TODO
    //  - debugAST
    if (isAllowPlainSqlAnnotation(data.allowPlainSqlAnnotation)) {
      // pass parent node @Allow.PlainSQL
      super.visitSimpleFunction(declaration, data)
    } else {
      data.allowPlainSqlAnnotation = getAllowPlainSqlAnnotation(declaration)
      super.visitSimpleFunction(declaration, data)
    }
  }

  override fun visitCall(expression: IrCall, data: IrContext) {
    // TODO
    // - debugAST
    checkPlainSQL(expression, data, mc)
  }
}
