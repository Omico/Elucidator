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

import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.UNIT
import me.omico.elucidator.GeneratedType
import me.omico.elucidator.KtFileScope
import me.omico.elucidator.addFunction
import me.omico.elucidator.addParameter
import me.omico.elucidator.lambdaTypeName
import me.omico.elucidator.returnStatement

internal fun KtFileScope.addTypeExtensionFunctions(type: GeneratedType) {
    if (type !is GeneratedType.Type) return
    typeNames.forEach { typeName ->
        addFunction("${typeName}Type") {
            addParameter<String>("name")
            addParameter(
                name = "block",
                type = lambdaTypeName(
                    receiver = GeneratedType.Type.generatedScopeClassName,
                    returnType = UNIT,
                ),
            )
            returnStatement<TypeSpec>("TypeSpec.${typeName}Builder(name = name).applyDslBuilder(block).build()")
        }
    }
}

private val typeNames: List<String> = listOf(
    "annotation",
    "class",
    "enum",
    "expectClass",
    "funInterface",
    "interface",
    "object",
)
