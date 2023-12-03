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
package me.omico.elucidator.psi

import com.squareup.kotlinpoet.UNIT
import me.omico.delusion.kotlin.compiler.embeddable.DelusionKotlinEnvironment
import me.omico.delusion.kotlin.compiler.embeddable.sourceFiles
import me.omico.elucidator.FunctionScope
import me.omico.elucidator.GeneratedType
import me.omico.elucidator.KtFileScope
import me.omico.elucidator.addParameter
import me.omico.elucidator.defaultValue
import me.omico.elucidator.lambdaTypeName
import me.omico.elucidator.psi.extension.addTypeExtensions
import me.omico.elucidator.psi.utility.findChildren
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtClass

internal fun KtFileScope.addDslExtensionsFromPsi(delusionKotlinEnvironment: DelusionKotlinEnvironment) {
    delusionKotlinEnvironment.sourceFiles.forEach { ktFile ->
        ktFile.findChildren<KtClass>()
            .mapNotNull(KtClass::getFqName)
            .map(FqName::asString)
            .forEach { fqName ->
                addTypeExtensions(
                    fqName = fqName,
                    ktFile = ktFile,
                    bindingContext = delusionKotlinEnvironment.bindingContext,
                )
            }
    }
}

internal inline fun KtFileScope.addWhile(
    actualFqName: String,
    expectGeneratedType: GeneratedType,
    block: () -> Unit,
) {
    if (builder.name != expectGeneratedType.generatedFileName) return
    if (actualFqName != expectGeneratedType.objectClass) return
    block()
}

internal fun FunctionScope.addScopeLambdaBlock(generatedType: GeneratedType): Unit =
    addParameter(
        name = "block",
        type = lambdaTypeName(
            receiver = generatedType.generatedScopeClassName,
            returnType = UNIT,
        ),
        block = { defaultValue("{}") },
    )
