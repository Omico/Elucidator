/*
 * Copyright 2023 Omico
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.omico.elucidator

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName

public fun parameter(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    block: ParameterScope.() -> Unit = {},
): ParameterSpec =
    ParameterSpec.builder(name = name, type = type, modifiers = modifiers).applyDslBuilder(block).build()

public fun parameter(
    name: String,
    type: TypeName,
    modifiers: Iterable<KModifier>,
    block: ParameterScope.() -> Unit = {},
): ParameterSpec =
    ParameterSpec.builder(name = name, type = type, modifiers = modifiers).applyDslBuilder(block).build()

public inline fun <reified T> parameter(
    name: String,
    vararg modifiers: KModifier,
    noinline block: ParameterScope.() -> Unit = {},
): ParameterSpec =
    parameter(name = name, type = T::class.asTypeName(), modifiers = modifiers, block = block)

public inline fun <reified T> parameter(
    name: String,
    modifiers: Iterable<KModifier>,
    noinline block: ParameterScope.() -> Unit = {},
): ParameterSpec =
    parameter(name = name, type = T::class.asTypeName(), modifiers = modifiers, block = block)

public fun ParameterScope.defaultValue(format: String, vararg args: Any?) {
    builder.defaultValue(format = format, args = args)
}

public fun ParameterScope.defaultValue(codeBlock: CodeBlock?) {
    builder.defaultValue(codeBlock = codeBlock)
}
