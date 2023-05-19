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
package me.omico.elucidator.function.basic

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.STAR
import com.squareup.kotlinpoet.TypeName
import kotlin.reflect.KClass

internal val BasicExtensionFunctions_FunctionScope: List<BasicExtensionFunction> by lazy {
    buildList {
        add(BasicExtensionFunction_addAnnotation)
        add(BasicExtensionFunction_addComment)
        add(BasicExtensionFunction_addStatement)
        addAll(BasicExtensionFunctions_addModifiers)
        addAll(BasicExtensionFunctions_addParameter)
        addAll(BasicExtensionFunctions_controlFlow)
    }
}

private val BasicExtensionFunctions_addParameter: List<BasicExtensionFunction> = listOf(
    BasicExtensionFunction("addParameter", "parameterSpec" to ParameterSpec::class),
    BasicExtensionFunction(
        name = "addParameter",
        parameters = arrayOf(
            "name" to String::class,
            "type" to TypeName::class,
            "vararg modifiers" to KModifier::class,
        ),
    ),
    BasicExtensionFunction(
        name = "addParameter",
        parameters = arrayOf(
            "name" to String::class,
            "type" to TypeName::class,
            "modifiers" to Iterable::class.parameterizedBy(KModifier::class),
        ),
    ),
    BasicExtensionFunction(
        name = "addParameter",
        parameters = arrayOf(
            "name" to String::class,
            "type" to KClass::class.parameterizedBy(Any::class).copy(typeArguments = listOf(STAR)),
            "vararg modifiers" to KModifier::class,
        ),
    ),
    BasicExtensionFunction(
        name = "addParameter",
        parameters = arrayOf(
            "name" to String::class,
            "type" to KClass::class.parameterizedBy(Any::class).copy(typeArguments = listOf(STAR)),
            "modifiers" to Iterable::class.parameterizedBy(KModifier::class),
        ),
    ),
)
