package com.nicchongwb.plugins

import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor

/*
Tree visitor pattern to traverse the IR tree
- during visitation, check for @Allow.PlainSQL & @PlainSQL annotation depending on the IrElement
- take note if parent node has @Allow.PlainSQL by passing state of data to child node
 */

class PlainSQLCheckerVisitor(): IrElementVisitor<Unit, String> {
  override fun visitElement(element: IrElement, data: String) {
    element.acceptChildren(this, data) // acceptChildren for recursive visitation
  }

  override fun visitClass(declaration: IrClass, data: String) {
    // TODO
    //  - debugAST
    val allowPlainSQLClassAnnotation = getAllowPlainSQLAnnotation(declaration)
    super.visitClass(declaration, allowPlainSQLClassAnnotation)
  }

  override fun visitSimpleFunction(declaration: IrSimpleFunction, data: String) {
    // TODO
    //  - debugAST
    if (isAllowPlainSQLAnnotation(data)) {
      // pass parent node @Allow.PlainSQL
      super.visitSimpleFunction(declaration, data)
    } else {
      val allowPlainSQLSimpleFunctionAnnotation = getAllowPlainSQLAnnotation(declaration)
      super.visitSimpleFunction(declaration, allowPlainSQLSimpleFunctionAnnotation)
    }
  }

  override fun visitCall(expression: IrCall, data: String) {
    // TODO
    // - debugAST
    // - check for PlainSQL annotation
  }
}