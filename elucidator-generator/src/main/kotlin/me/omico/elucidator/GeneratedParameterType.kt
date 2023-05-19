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
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import kotlin.reflect.KClass

data class GeneratedParameterType(
    val name: String,
    val typeName: TypeName,
    val defaultValue: CodeBlock? = null,
    val vararg: Boolean = false,
)

infix fun String.with(type: TypeName): GeneratedParameterType = GeneratedParameterType(name = this, typeName = type)

infix fun <T : Any> String.with(type: KClass<T>): GeneratedParameterType = this with type.asTypeName()

infix fun GeneratedParameterType.defaultValue(defaultValue: CodeBlock): GeneratedParameterType =
    copy(defaultValue = defaultValue)

infix fun GeneratedParameterType.defaultValue(value: String): GeneratedParameterType = defaultValue(CodeBlock.of(value))

infix fun GeneratedParameterType.vararg(vararg: Boolean): GeneratedParameterType = copy(vararg = vararg)

fun KClass<*>.parameterizedBy(vararg typeArguments: TypeName): ParameterizedTypeName =
    asClassName().parameterizedBy(typeArguments = typeArguments.toList())
