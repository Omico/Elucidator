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

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import me.omico.elucidator.FunctionScope
import me.omico.elucidator.GENERATED_PACKAGE_NAME
import me.omico.elucidator.GeneratedParameterType
import me.omico.elucidator.GeneratedType
import me.omico.elucidator.KtFileScope
import me.omico.elucidator.addFunction
import me.omico.elucidator.addParameter
import me.omico.elucidator.addStatement
import me.omico.elucidator.defaultValue
import me.omico.elucidator.receiver
import me.omico.elucidator.vararg
import me.omico.elucidator.with

internal fun KtFileScope.addDslScopeBasicExtensionFunctions(type: GeneratedType) {
    dslScopeBasicExtensionFunctions[type.generatedScopeName]?.forEach { function ->
        addDslScopeBasicExtensionFunction(type.generatedScopeName, function)
    }
}

private fun KtFileScope.addDslScopeBasicExtensionFunction(scope: String, function: BasicExtensionFunction): Unit =
    addFunction(function.name) {
        receiver(ClassName(GENERATED_PACKAGE_NAME, scope))
        addBasicExtensionFunctionParameters(function)
        addBasicExtensionFunctionStatement(function)
    }

private fun FunctionScope.addBasicExtensionFunctionParameters(function: BasicExtensionFunction) =
    function.parameters.forEach { parameter ->
        addParameter(parameter.name, parameter.typeName) {
            if (parameter.vararg) builder.addModifiers(KModifier.VARARG)
            defaultValue(parameter.defaultValue)
        }
    }

private fun FunctionScope.addBasicExtensionFunctionStatement(function: BasicExtensionFunction): Unit =
    function.parameters
        .map(GeneratedParameterType::name)
        .joinToString(", ") { name -> "$name = $name" }
        .let { parameters -> addStatement("builder.${function.name}($parameters)") }

private val dslScopeBasicExtensionFunctions: BasicExtensionFunctions by lazy {
    mapOf(
        "AnnotationScope" to BasicExtensionFunctions_AnnotationScope,
        "FunctionScope" to BasicExtensionFunctions_FunctionScope,
        "KtFileScope" to BasicExtensionFunctions_KtFileScope,
        "PropertyScope" to BasicExtensionFunctions_PropertyScope,
        "TypeScope" to BasicExtensionFunctions_TypeScope,
    )
}

internal val BasicExtensionFunction_addAnnotation: BasicExtensionFunction =
    BasicExtensionFunction("addAnnotation", "annotationSpec" with AnnotationSpec::class)

internal val BasicExtensionFunctions_addModifiers: List<BasicExtensionFunction> = listOf(
    BasicExtensionFunction("addModifiers", "modifiers" with Iterable::class.parameterizedBy(KModifier::class)),
    BasicExtensionFunction("addModifiers", "modifiers" with KModifier::class vararg true),
)

internal val BasicExtensionFunction_addStatement: BasicExtensionFunction =
    BasicExtensionFunction("addStatement", "format" with String::class, "args" with Any::class vararg true)

internal val BasicExtensionFunction_addFunction: BasicExtensionFunction =
    BasicExtensionFunction("addFunction", "funSpec" with FunSpec::class)

internal val BasicExtensionFunction_addProperty: BasicExtensionFunction =
    BasicExtensionFunction("addProperty", "propertySpec" with PropertySpec::class)

internal val BasicExtensionFunction_addType: BasicExtensionFunction =
    BasicExtensionFunction("addType", "typeSpec" with TypeSpec::class)

internal val BasicExtensionFunction_addSuperclassConstructorParameter: BasicExtensionFunction =
    BasicExtensionFunction(
        name = "addSuperclassConstructorParameter",
        parameters = arrayOf(
            "format" with String::class,
            "args" with Any::class vararg true,
        ),
    )

internal val BasicExtensionFunction_addFileComment: BasicExtensionFunction =
    BasicExtensionFunction("addFileComment", "format" with String::class, "args" with Any::class vararg true)

internal val BasicExtensionFunction_addComment: BasicExtensionFunction =
    BasicExtensionFunction("addComment", "format" with String::class, "args" with Any::class vararg true)

internal val BasicExtensionFunctions_controlFlow: List<BasicExtensionFunction> = listOf(
    BasicExtensionFunction("beginControlFlow", "controlFlow" with String::class, "args" with Any::class vararg true),
    BasicExtensionFunction("nextControlFlow", "controlFlow" with String::class, "args" with Any::class vararg true),
    BasicExtensionFunction("endControlFlow"),
)
