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
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

@AutoService(CommandLineProcessor::class)
class KotlinJooqCheckerCommandLineProcessor : CommandLineProcessor {
  companion object {
    private const val OPTION_DEBUGAST = "boolean"

    val ARG_DEBUGAST = CompilerConfigurationKey<Boolean>(OPTION_DEBUGAST)
  }

  override val pluginId: String = BuildConfig.KOTLIN_PLUGIN_ID

  override val pluginOptions: Collection<CliOption> = listOf(
    CliOption(
      optionName = OPTION_DEBUGAST,
      valueDescription = "<true|false>",
      description = "For debugging purposes: dumps IR AST Traversal to build logs",
      required = false,
    ),
  )

  override fun processOption(
    option: AbstractCliOption,
    value: String,
    configuration: CompilerConfiguration
  ) {
    return when (option.optionName) {
      OPTION_DEBUGAST -> configuration.put(ARG_DEBUGAST, value.toBoolean())
      else -> throw IllegalArgumentException("Unexpected config option ${option.optionName}")
    }
  }
}
