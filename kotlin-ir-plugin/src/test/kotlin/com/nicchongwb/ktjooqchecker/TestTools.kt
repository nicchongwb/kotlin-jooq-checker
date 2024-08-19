@file:OptIn(ExperimentalCompilerApi::class)

package com.nicchongwb.ktjooqchecker

import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

// Plugin configuration
val debugDumpIR = true

fun compile(
  sourceFiles: List<SourceFile>,
  plugin: CompilerPluginRegistrar = KotlinJooqCheckerCompilerRegistrar(defaultDebugDumpIR = debugDumpIR),
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
  plugin: CompilerPluginRegistrar = KotlinJooqCheckerCompilerRegistrar(defaultDebugDumpIR = debugDumpIR),
): JvmCompilationResult {
  return compile(listOf(sourceFile), plugin)
}
