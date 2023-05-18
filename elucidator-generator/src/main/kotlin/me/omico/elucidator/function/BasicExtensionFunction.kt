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
package me.omico.elucidator.function

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import me.omico.elucidator.FunctionScope
import me.omico.elucidator.GENERATED_PACKAGE_NAME
import me.omico.elucidator.GeneratedType
import me.omico.elucidator.KtFileScope
import me.omico.elucidator.addFunction
import me.omico.elucidator.addParameter
import me.omico.elucidator.addStatement
import me.omico.elucidator.receiver
import kotlin.reflect.KClass

internal fun KtFileScope.addDslScopeBasicExtensionFunctions(type: GeneratedType) {
    basicExtensionFunctions[type.generatedScopeName]?.forEach { function ->
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
    function.parameters.forEach { (name, type) -> addBasicExtensionFunctionParameter(name, type) }

private fun FunctionScope.addBasicExtensionFunctionParameter(name: String, type: Any): Unit =
    when (type) {
        is KClass<*> -> when {
            isVariableArray(name) -> addParameter(actualName(name), type, KModifier.VARARG)
            else -> addParameter(name, type)
        }
        is TypeName -> when {
            isVariableArray(name) -> addParameter(actualName(name), type, KModifier.VARARG)
            else -> addParameter(name, type)
        }
        else -> Unit
    }

private fun FunctionScope.addBasicExtensionFunctionStatement(function: BasicExtensionFunction) {
    val parameters = function.parameters.keys.joinToString(", ") { name ->
        when {
            isVariableArray(name) -> "${actualName(name)} = ${actualName(name)}"
            else -> "$name = $name"
        }
    }
    addStatement("builder.${function.name}($parameters)")
}

private data class BasicExtensionFunction(
    val name: String,
    val parameters: Map<String, Any>,
)

private typealias BasicExtensionFunctions = Map<String, List<BasicExtensionFunction>>

private fun BasicExtensionFunction(
    name: String,
    vararg parameters: Pair<String, Any>,
): BasicExtensionFunction =
    BasicExtensionFunction(name, mapOf(*parameters))

private val basicExtensionFunctions: BasicExtensionFunctions by lazy {
    mapOf(
        "AnnotationScope" to BasicExtensionFunctions_AnnotationScope,
        "FunctionScope" to BasicExtensionFunctions_FunctionScope,
        "KtFileScope" to BasicExtensionFunctions_KtFileScope,
        "PropertyScope" to BasicExtensionFunctions_PropertyScope,
        "TypeScope" to BasicExtensionFunctions_TypeScope,
    )
}

private val BasicExtensionFunctions_AnnotationScope: List<BasicExtensionFunction> by lazy {
    buildList {
        add(BasicExtensionFunction_addMember)
    }
}

private val BasicExtensionFunctions_FunctionScope: List<BasicExtensionFunction> by lazy {
    buildList {
        add(BasicExtensionFunction_addAnnotation)
        add(BasicExtensionFunction_addComment)
        add(BasicExtensionFunction_addStatement)
        addAll(BasicExtensionFunctions_addModifiers)
        addAll(BasicExtensionFunctions_controlFlow)
        listOf(
            BasicExtensionFunction("addParameter", "parameterSpec" to ParameterSpec::class),
        ).let(::addAll)
    }
}

private val BasicExtensionFunctions_KtFileScope: List<BasicExtensionFunction> by lazy {
    buildList {
        add(BasicExtensionFunction_addAnnotation)
        add(BasicExtensionFunction_addFileComment)
        add(BasicExtensionFunction_addFunction)
        add(BasicExtensionFunction_addProperty)
        add(BasicExtensionFunction_addType)
    }
}

private val BasicExtensionFunctions_PropertyScope: List<BasicExtensionFunction> by lazy {
    buildList {
        add(BasicExtensionFunction_addAnnotation)
        addAll(BasicExtensionFunctions_addModifiers)
        listOf(
            BasicExtensionFunction(
                name = "initializer",
                parameters = arrayOf(
                    "format" to String::class,
                    "vararg args" to Any::class.asTypeName().copy(nullable = true),
                ),
            ),
        ).let(::addAll)
    }
}

private val BasicExtensionFunctions_TypeScope: List<BasicExtensionFunction> by lazy {
    buildList {
        add(BasicExtensionFunction_addAnnotation)
        add(BasicExtensionFunction_addFunction)
        add(BasicExtensionFunction_addProperty)
        add(BasicExtensionFunction_addSuperclassConstructorParameter)
        add(BasicExtensionFunction_addType)
        addAll(BasicExtensionFunctions_addModifiers)
    }
}

private val BasicExtensionFunction_addAnnotation: BasicExtensionFunction =
    BasicExtensionFunction("addAnnotation", "annotationSpec" to AnnotationSpec::class)

private val BasicExtensionFunctions_addModifiers: List<BasicExtensionFunction> = listOf(
    BasicExtensionFunction("addModifiers", "modifiers" to Iterable::class.parameterizedBy(KModifier::class)),
    BasicExtensionFunction("addModifiers", "vararg modifiers" to KModifier::class),
)

private val BasicExtensionFunction_addStatement: BasicExtensionFunction =
    BasicExtensionFunction("addStatement", "format" to String::class, "vararg args" to Any::class)

private val BasicExtensionFunction_addFunction: BasicExtensionFunction =
    BasicExtensionFunction("addFunction", "funSpec" to FunSpec::class)

private val BasicExtensionFunction_addProperty: BasicExtensionFunction =
    BasicExtensionFunction("addProperty", "propertySpec" to PropertySpec::class)

private val BasicExtensionFunction_addType: BasicExtensionFunction =
    BasicExtensionFunction("addType", "typeSpec" to TypeSpec::class)

private val BasicExtensionFunction_addSuperclassConstructorParameter: BasicExtensionFunction =
    BasicExtensionFunction(
        "addSuperclassConstructorParameter",
        "format" to String::class,
        "vararg args" to Any::class,
    )

private val BasicExtensionFunction_addFileComment: BasicExtensionFunction =
    BasicExtensionFunction("addFileComment", "format" to String::class, "vararg args" to Any::class)

private val BasicExtensionFunction_addComment: BasicExtensionFunction =
    BasicExtensionFunction("addComment", "format" to String::class, "vararg args" to Any::class)

private val BasicExtensionFunction_addMember: BasicExtensionFunction =
    BasicExtensionFunction("addMember", "format" to String::class, "vararg args" to Any::class)

private val BasicExtensionFunctions_controlFlow: List<BasicExtensionFunction> = listOf(
    BasicExtensionFunction("beginControlFlow", "controlFlow" to String::class, "vararg args" to Any::class),
    BasicExtensionFunction("nextControlFlow", "controlFlow" to String::class, "vararg args" to Any::class),
    BasicExtensionFunction("endControlFlow"),
)
