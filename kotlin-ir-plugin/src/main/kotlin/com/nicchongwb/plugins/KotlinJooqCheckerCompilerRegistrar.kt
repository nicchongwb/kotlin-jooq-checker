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

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration

@AutoService(CompilerPluginRegistrar::class)
class KotlinJooqCheckerCompilerRegistrar(
  private val defaultDebugAST: Boolean
) : CompilerPluginRegistrar() {
  override val supportsK2 = true

  @Suppress("unused") // Used by service loader
  constructor() : this(
    defaultDebugAST = false
  )

  override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
    val mc = configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)
    val debugAST = configuration.get(KotlinJooqCheckerCommandLineProcessor.ARG_DEBUGAST, defaultDebugAST)

    IrGenerationExtension.registerExtension(KotlinJooqCheckerIrGenerationExtension(mc, debugAST))
  }
}
