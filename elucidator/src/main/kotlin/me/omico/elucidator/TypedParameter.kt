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

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import kotlin.reflect.KClass

public data class TypedParameter(
    val name: String,
    val type: TypeName,
    val nullable: Boolean = false,
    val defaultValue: CodeBlock? = null,
    val vararg: Boolean = false,
    val annotations: List<AnnotationSpec> = emptyList(),
    val kdoc: CodeBlock? = null,
)

public infix fun String.with(type: TypeName): TypedParameter = TypedParameter(name = this, type = type)

public infix fun <T : Any> String.with(type: KClass<T>): TypedParameter = this with type.asTypeName()

public infix fun TypedParameter.nullable(nullable: Boolean): TypedParameter =
    copy(type = type.copy(nullable = nullable))

public infix fun TypedParameter.defaultValue(defaultValue: CodeBlock): TypedParameter =
    copy(defaultValue = defaultValue)

public infix fun TypedParameter.defaultValue(defaultValue: String): TypedParameter =
    CodeBlock.of(defaultValue).let(::defaultValue)

public infix fun TypedParameter.vararg(vararg: Boolean): TypedParameter = copy(vararg = vararg)

public infix fun TypedParameter.annotation(annotationSpec: AnnotationSpec): TypedParameter =
    copy(annotations = listOf(annotationSpec))

public infix fun TypedParameter.annotations(annotations: List<AnnotationSpec>): TypedParameter =
    copy(annotations = annotations)

public infix operator fun TypedParameter.plus(annotationSpec: AnnotationSpec): TypedParameter =
    copy(annotations = annotations + annotationSpec)

public infix fun TypedParameter.kdoc(kdoc: CodeBlock?): TypedParameter = copy(kdoc = kdoc)

public fun FunctionScope.addParameter(parameter: TypedParameter): Unit =
    addParameter(name = parameter.name, type = parameter.type) {
        if (parameter.vararg) modifier(KModifier.VARARG)
        defaultValue(parameter.defaultValue)
        addAnnotations(parameter.annotations)
        parameter.kdoc?.let(::addKdoc)
    }

public fun FunctionScope.addParameters(vararg parameters: TypedParameter): Unit = parameters.forEach(::addParameter)

public fun FunctionScope.addParameters(parameters: Iterable<TypedParameter>): Unit = parameters.forEach(::addParameter)
