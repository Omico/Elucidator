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
import me.omico.elucidator.parameterizedBy
import me.omico.elucidator.vararg
import me.omico.elucidator.with
import kotlin.reflect.KClass

internal val BasicExtensionFunctions_FunctionScope: List<BasicExtensionFunction> by lazy {
    buildList {
        add(BasicExtensionFunction_addAnnotation)
        add(BasicExtensionFunction_addAnnotations)
        add(BasicExtensionFunction_addComment)
        add(BasicExtensionFunction_addStatement)
        addAll(BasicExtensionFunctions_addCode)
        addAll(BasicExtensionFunctions_addKdoc)
        addAll(BasicExtensionFunctions_addModifiers)
        addAll(BasicExtensionFunctions_addParameter)
        addAll(BasicExtensionFunctions_controlFlow)
    }
}

private val BasicExtensionFunctions_addParameter: List<BasicExtensionFunction> = listOf(
    BasicExtensionFunction("addParameter", "parameterSpec" with ParameterSpec::class),
    BasicExtensionFunction(
        name = "addParameter",
        parameters = arrayOf(
            "name" with String::class,
            "type" with TypeName::class,
            "modifiers" with KModifier::class vararg true,
        ),
    ),
    BasicExtensionFunction(
        name = "addParameter",
        parameters = arrayOf(
            "name" with String::class,
            "type" with TypeName::class,
            "modifiers" with Iterable::class.parameterizedBy(KModifier::class),
        ),
    ),
    BasicExtensionFunction(
        name = "addParameter",
        parameters = arrayOf(
            "name" with String::class,
            "type" with KClass::class.parameterizedBy(STAR),
            "modifiers" with KModifier::class vararg true,
        ),
    ),
    BasicExtensionFunction(
        name = "addParameter",
        parameters = arrayOf(
            "name" with String::class,
            "type" with KClass::class.parameterizedBy(STAR),
            "modifiers" with Iterable::class.parameterizedBy(KModifier::class),
        ),
    ),
)
