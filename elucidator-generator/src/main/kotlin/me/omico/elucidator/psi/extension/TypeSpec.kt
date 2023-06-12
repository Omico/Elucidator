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
package me.omico.elucidator.psi.extension

import com.squareup.kotlinpoet.TypeSpec
import me.omico.elucidator.GeneratedType
import me.omico.elucidator.KtFileScope
import me.omico.elucidator.TypedParameter
import me.omico.elucidator.addFunction
import me.omico.elucidator.addParameter
import me.omico.elucidator.psi.addScopeLambdaBlock
import me.omico.elucidator.psi.addWhile
import me.omico.elucidator.psi.info.KtFunctionInfo
import me.omico.elucidator.psi.info.toTypedParameters
import me.omico.elucidator.psi.utility.requireChild
import me.omico.elucidator.psi.utility.typeFqName
import me.omico.elucidator.receiver
import me.omico.elucidator.returnStatement
import me.omico.elucidator.utility.capitalize
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction

internal fun KtFileScope.addTypeExtensions(fqName: String, ktFile: KtFile): Unit =
    addWhile(actualFqName = fqName, expectGeneratedType = GeneratedType.Type) {
        val companionObject = ktFile.requireChild<KtClass>().companionObjects.first()
        val generatedTypeSpecExtensions = companionObject.declarations
            .asSequence()
            .filterIsInstance<KtNamedFunction>()
            .filter { it.modifierList?.hasModifier(KtTokens.PUBLIC_KEYWORD) == true }
            .filter { it.typeFqName == "com.squareup.kotlinpoet.TypeSpec.Builder" }
            .map(::KtFunctionInfo)
            .map(::GeneratedTypeSpecExtension)
        generatedTypeSpecExtensions.forEach { generatedTypeSpecExtension ->
            addFunction(generatedTypeSpecExtension.typeName) {
                generatedTypeSpecExtension.parameters.forEach(::addParameter)
                addScopeLambdaBlock(GeneratedType.Type)
                returnStatement<TypeSpec>(generatedTypeSpecExtension.typeBuilderStatement)
            }
        }
        generatedTypeSpecExtensions.forEach { generatedTypeSpecExtension ->
            addFunction(generatedTypeSpecExtension.addTypeFunctionName) {
                receiver(GeneratedType.Type.generatedScopeClassName)
                generatedTypeSpecExtension.parameters.forEach(::addParameter)
                addScopeLambdaBlock(GeneratedType.Type)
                returnStatement<Unit>(generatedTypeSpecExtension.addTypeStatement)
            }
        }
    }

private data class GeneratedTypeSpecExtension(
    val typeBuilderName: String,
    val parameters: List<TypedParameter>,
) {
    val type: String = typeBuilderName.removeSuffix("Builder")
    val typeName: String = "${type}Type"
    val parametersString: String = parameters.toParametersString()
    val typeBuilderStatement: String = "TypeSpec.$typeBuilderName($parametersString).applyDslBuilder(block).build()"
    val addTypeFunctionName: String = "add${type.capitalize()}"
    val addTypeStatement: String = buildString {
        append(typeName)
        append("(")
        if (parameters.isNotEmpty()) append(parametersString).append(", ")
        append("block")
        append(")")
        append(".let(::addType)")
    }
}

private fun GeneratedTypeSpecExtension(functionInfo: KtFunctionInfo): GeneratedTypeSpecExtension =
    GeneratedTypeSpecExtension(
        typeBuilderName = functionInfo.name,
        parameters = functionInfo.parameters.toTypedParameters(),
    )

private fun List<TypedParameter>.toParametersString(): String = joinToString(transform = TypedParameter::name)
