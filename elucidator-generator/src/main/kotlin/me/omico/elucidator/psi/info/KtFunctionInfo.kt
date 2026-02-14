/*
 * Copyright 2023-2026 Omico
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

import me.omico.delusion.kotlin.compiler.analysis.resolveFqName
import org.jetbrains.kotlin.analysis.api.analyze
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtParameter

internal data class KtFunctionInfo(
    val name: String,
    val parameters: List<KtParameterInfo>,
    val returnType: String,
    val isNullable: Boolean,
)

internal fun KtNamedFunction.createKtFunctionInfo(): KtFunctionInfo =
    analyze(this) {
        KtFunctionInfo(
            name = name!!,
            parameters = valueParameters.map(KtParameter::createKtParameterInfo),
            returnType = returnType.resolveFqName()!!.asString(),
            isNullable = returnType.isMarkedNullable,
        )
    }
