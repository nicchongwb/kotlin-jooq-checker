package com.nicchongwb.plugins

import org.jetbrains.kotlin.ir.declarations.IrFile

data class IrContext(
  var irFile: IrFile?=null,
  var srcFile: SourceFile?=null,
  var allowPlainSqlAnnotation: String = "",
  var plainSqlAnnotation: String = "",
  )
