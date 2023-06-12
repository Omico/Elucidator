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

import me.omico.elucidator.psi.utility.isNullable
import me.omico.elucidator.psi.utility.typeFqName
import org.jetbrains.kotlin.psi.KtNamedFunction

internal data class KtFunctionInfo(
    val name: String,
    val parameters: List<KtParameterInfo>,
    val returnType: String,
    val isNullable: Boolean,
)

internal fun KtFunctionInfo(function: KtNamedFunction): KtFunctionInfo =
    KtFunctionInfo(
        name = function.name!!,
        parameters = function.valueParameters.map(::KtParameterInfo),
        returnType = function.typeFqName,
        isNullable = function.isNullable,
    )
