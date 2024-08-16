/*
 * Copyright (C) 2024 Nicholas Chong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:OptIn(ExperimentalCompilerApi::class)

package com.nicchongwb.plugins

import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import kotlin.test.assertEquals
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.Test
import java.io.File


class IrPluginTestAllowPlainSql {
  val filePath = "src/test/kotlin/com/nicchongwb/plugins/sources/AllowPlainSqlSourceFile.kt"
  val file = File(filePath)
  val contents = file.readText()
  val fileName = file.name

  val sourceFiles = listOf(
    SourceFile.kotlin(fileName, contents)
  )

  @Test
  fun `Compile Success for AllowPlainSql`() {
    val result = compile(sourceFiles)

    assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)
  }
}

// Plugin configuration
val debugAST = true

fun compile(
  sourceFiles: List<SourceFile>,
  plugin: CompilerPluginRegistrar = KotlinJooqCheckerCompilerRegistrar(defaultDebugAST = debugAST),
): JvmCompilationResult {
  return KotlinCompilation().apply {
    sources = sourceFiles
    compilerPluginRegistrars = listOf(plugin)
    inheritClassPath = true
//    messageOutputStream = System.out
  }.compile()
}

fun compile(
  sourceFile: SourceFile,
  plugin: CompilerPluginRegistrar = KotlinJooqCheckerCompilerRegistrar(defaultDebugAST = debugAST),
): JvmCompilationResult {
  return compile(listOf(sourceFile), plugin)
}
