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

import me.omico.delusion.kotlin.compiler.embeddable.fqName
import me.omico.delusion.kotlin.compiler.embeddable.resolveType
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.types.isNullable

internal data class KtFunctionInfo(
    val name: String,
    val parameters: List<KtParameterInfo>,
    val returnType: String,
    val isNullable: Boolean,
)

internal fun KtNamedFunction.createKtFunctionInfo(bindingContext: BindingContext): KtFunctionInfo {
    val returnType = resolveType(bindingContext)
    return KtFunctionInfo(
        name = name!!,
        parameters = valueParameters.map { parameter -> parameter.createKtParameterInfo(bindingContext) },
        returnType = returnType.fqName.asString(),
        isNullable = returnType.isNullable(),
    )
}
