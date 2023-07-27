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

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.UNIT
import com.squareup.kotlinpoet.asClassName
import me.omico.elucidator.FunctionScope
import me.omico.elucidator.GeneratedType
import me.omico.elucidator.KtFileScope
import me.omico.elucidator.addFunction
import me.omico.elucidator.addMember
import me.omico.elucidator.addParameter
import me.omico.elucidator.addStatement
import me.omico.elucidator.annotate
import me.omico.elucidator.lambdaTypeName
import me.omico.elucidator.modifiers
import me.omico.elucidator.psi.utility.findChildren
import me.omico.elucidator.psi.utility.hasSuperType
import me.omico.elucidator.receiver
import me.omico.elucidator.vararg
import me.omico.elucidator.with
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile

internal fun KtFileScope.addAnnotateExtensionFunctions(annotatableSpecs: Set<String>, type: GeneratedType) {
    if (type.objectClass !in annotatableSpecs) return
    addDeprecatedAnnotateExtensionFunctions(type)
    addFunction("annotate") {
        modifiers(KModifier.INLINE)
        TypeVariableName("T")
            .copy(
                reified = true,
                bounds = listOf(Annotation::class.asClassName()),
            )
            .let(builder::addTypeVariable)
        receiver(type.generatedScopeClassName)
        addParameter(
            name = "block",
            type = lambdaTypeName(
                receiver = GeneratedType.Annotation.generatedScopeClassName,
                returnType = UNIT,
            ),
            modifiers = arrayOf(KModifier.NOINLINE),
        )
        addStatement("annotation(type = T::class, block = block).let(this::annotate)")
    }
    addFunction("annotate") {
        receiver(type.generatedScopeClassName)
        addParameter<AnnotationSpec>("annotationSpec")
        addStatement("builder.addAnnotation(annotationSpec = annotationSpec)")
    }
    addFunction("annotate") {
        receiver(type.generatedScopeClassName)
        addParameter("annotationSpecs" with Iterable::class.parameterizedBy(AnnotationSpec::class))
        addStatement("builder.addAnnotations(annotationSpecs = annotationSpecs)")
    }
    addFunction("annotate") {
        receiver(type.generatedScopeClassName)
        addParameter("annotationSpecs" with AnnotationSpec::class vararg true)
        addStatement("builder.addAnnotations(annotationSpecs = annotationSpecs.toList())")
    }
    addFunction("annotate") {
        receiver(type.generatedScopeClassName)
        addParameter<ClassName>("annotation")
        addStatement("builder.addAnnotation(annotation = annotation)")
    }
    addFunction("annotate") {
        modifiers(KModifier.INLINE)
        builder.addTypeVariable(TypeVariableName("T").copy(reified = true))
        receiver(type.generatedScopeClassName)
        addStatement("builder.addAnnotation(annotation = T::class)")
    }
}

internal inline val Map<String, KtFile>.annotatableSpecs: Set<String>
    get() = values
        .asSequence()
        .flatMap { ktFile -> ktFile.findChildren<KtClass>() }
        .filter { ktClass -> ktClass.hasSuperType("Annotatable") && ktClass.name!!.endsWith("Spec") }
        .mapNotNull { it.fqName?.asString() }
        .toSortedSet()

private fun KtFileScope.addDeprecatedAnnotateExtensionFunctions(type: GeneratedType) {
    addFunction("addAnnotation") {
        addDeprecatedAnnotation("annotationSpec")
        receiver(type.generatedScopeClassName)
        addParameter<AnnotationSpec>("annotationSpec")
        addStatement("builder.addAnnotation(annotationSpec = annotationSpec)")
    }
    addFunction("addAnnotations") {
        addDeprecatedAnnotation("annotationSpecs")
        receiver(type.generatedScopeClassName)
        addParameter("annotationSpecs" with Iterable::class.parameterizedBy(AnnotationSpec::class))
        addStatement("builder.addAnnotations(annotationSpecs = annotationSpecs)")
    }
}

private fun FunctionScope.addDeprecatedAnnotation(parameterName: String): Unit =
    annotate<Deprecated> {
        addMember("message = \"\"\"In Elucidator, we use the `addAnnotation` function to create new annotations. Use the `annotate` function instead.\"\"\"")
        addMember("replaceWith = ReplaceWith(\"annotate($parameterName)\")")
        addMember("level = DeprecationLevel.WARNING")
    }
