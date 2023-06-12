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
package me.omico.elucidator.psi.info

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import me.omico.elucidator.TypedParameter
import me.omico.elucidator.psi.utility.isNullable
import me.omico.elucidator.psi.utility.typeFqName
import org.jetbrains.kotlin.psi.KtParameter

internal data class KtParameterInfo(
    val name: String,
    val type: String,
    val isVarArg: Boolean,
    val isNullable: Boolean,
    val defaultValue: String?,
)

internal fun KtParameterInfo(parameter: KtParameter): KtParameterInfo =
    KtParameterInfo(
        name = parameter.name!!,
        type = parameter.typeFqName,
        isVarArg = parameter.isVarArg,
        isNullable = parameter.isNullable,
        defaultValue = parameter.defaultValue?.text,
    )

internal fun KtParameterInfo.toTypedParameter(): TypedParameter =
    TypedParameter(
        name = name,
        type = ClassName.bestGuess(type).copy(nullable = isNullable),
        vararg = isVarArg,
        defaultValue = defaultValue?.let(CodeBlock::of),
    )

internal fun List<KtParameterInfo>.toTypedParameters(): List<TypedParameter> = map(KtParameterInfo::toTypedParameter)
