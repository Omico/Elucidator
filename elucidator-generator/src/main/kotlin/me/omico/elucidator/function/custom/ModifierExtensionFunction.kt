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

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import me.omico.elucidator.GeneratedType
import me.omico.elucidator.KtFileScope
import me.omico.elucidator.addFunction
import me.omico.elucidator.addParameter
import me.omico.elucidator.addStatement
import me.omico.elucidator.receiver
import me.omico.elucidator.returnStatement
import me.omico.elucidator.returnType

internal fun KtFileScope.addModifierExtensionFunction(type: GeneratedType) {
    if (type !in allowedTypes) return
    addFunction("clearModifiers") {
        receiver(type.generatedScopeClassName)
        returnStatement<Unit>("builder.modifiers.clear()")
    }
    addFunction("modifiers") {
        addParameter("modifiers", KModifier::class, KModifier.VARARG)
        receiver(type.generatedScopeClassName)
        addStatement("clearModifiers()")
        addStatement("addModifiers(modifiers = modifiers)")
        returnType<Unit>()
    }
    addFunction("modifiers") {
        addParameter("modifiers", Iterable::class.parameterizedBy(KModifier::class))
        receiver(type.generatedScopeClassName)
        addStatement("clearModifiers()")
        addStatement("addModifiers(modifiers = modifiers)")
        returnType<Unit>()
    }
    addFunction("modifier") {
        addParameter("modifier", KModifier::class)
        receiver(type.generatedScopeClassName)
        returnStatement<Unit>("modifiers(modifier)")
    }
}

private val allowedTypes = listOf(
    GeneratedType.Function,
    GeneratedType.Property,
    GeneratedType.Type,
)
