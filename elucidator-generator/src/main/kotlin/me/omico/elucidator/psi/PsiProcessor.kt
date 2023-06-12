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
import me.omico.elucidator.FunctionScope
import me.omico.elucidator.GeneratedType
import me.omico.elucidator.KtFileScope
import me.omico.elucidator.addParameter
import me.omico.elucidator.defaultValue
import me.omico.elucidator.lambdaTypeName
import me.omico.elucidator.psi.extension.addTypeExtensions
import org.jetbrains.dokka.DokkaSourceSetID
import org.jetbrains.dokka.DokkaSourceSetImpl
import org.jetbrains.dokka.analysis.ProjectKotlinAnalysis
import org.jetbrains.dokka.utilities.DokkaConsoleLogger
import org.jetbrains.dokka.utilities.LoggingLevel
import org.jetbrains.kotlin.nj2k.fqNameWithoutCompanions
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtFile
import java.io.File

internal fun KtFileScope.addDslExtensionsFromPsi(ktFileMap: Map<String, KtFile>) {
    ktFileMap.forEach { (fqName, ktFile) ->
        addTypeExtensions(fqName, ktFile)
    }
}

internal fun createKotlinpoetKtFileMap(kotlinpoetDirectory: File): Map<String, KtFile> {
    val sourceRoots = kotlinpoetDirectory.walk()
        .filter { "src/test" !in it.invariantSeparatorsPath }
        .filter(File::isFile)
        .filter { it.extension == "kt" }
        .toSet()
    val dokkaSourceSet = DokkaSourceSetImpl(
        displayName = "KotlinpoetSourceSet",
        sourceSetID = DokkaSourceSetID("KotlinpoetScopeID", "KotlinpoetSourceSet"),
        sourceRoots = sourceRoots,
    )
    val dokkaConsoleLogger = DokkaConsoleLogger(minLevel = LoggingLevel.WARN)
    val kotlinAnalysis = ProjectKotlinAnalysis(listOf(dokkaSourceSet), dokkaConsoleLogger)
    val ktFileMap = kotlinAnalysis[dokkaSourceSet].environment.getSourceFiles()
        .flatMap { ktFile ->
            ktFile.declarations
                .map(KtDeclaration::fqNameWithoutCompanions)
                .map { it.asString() to ktFile }
        }
        .associate { (fqName, ktFile) -> fqName to ktFile }
        .toSortedMap()
    return ktFileMap
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
