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
package me.omico.elucidator.function.custom

import me.omico.elucidator.GeneratedType
import me.omico.elucidator.KtFileScope
import me.omico.elucidator.addFunction
import me.omico.elucidator.addParameter
import me.omico.elucidator.psi.addScopeLambdaBlock
import me.omico.elucidator.receiver
import me.omico.elucidator.returnStatement
import me.omico.elucidator.utility.capitalize

internal fun KtFileScope.addTypeExtensionFunctions(type: GeneratedType) {
    addAddTypeExtensionFunctions(type)
}

private fun KtFileScope.addAddTypeExtensionFunctions(type: GeneratedType) {
    if (type !is GeneratedType.KtFile) return
    typeNames.forEach { typeName ->
        addFunction("add${typeName.capitalize()}") {
            receiver(type.generatedScopeClassName)
            addParameter<String>("name")
            addScopeLambdaBlock(GeneratedType.Type)
            returnStatement<Unit>("${typeName}Type(name, block).let(::addType)")
        }
    }
}

private val typeNames: List<String> = listOf(
    "annotation",
    "class",
    "enum",
    "funInterface",
    "interface",
    "object",
)
