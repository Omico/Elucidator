//
// Generated by DslGenerator from Elucidator. Do not edit directly!!!
//
package me.omico.elucidator

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import kotlin.Annotation
import kotlin.Any
import kotlin.Deprecated
import kotlin.String
import kotlin.Unit
import kotlin.collections.Iterable

@ElucidatorDsl
public interface PropertyScope {
  public val builder: PropertySpec.Builder

  public fun build(): PropertySpec
}

internal class PropertyBuilder(
  override val builder: PropertySpec.Builder,
) : PropertyScope {
  override fun build(): PropertySpec = builder.build()
}

public fun PropertyScope.addKdoc(format: String, vararg args: Any) {
  builder.addKdoc(format = format, args = args)
}

public fun PropertyScope.addKdoc(block: CodeBlock) {
  builder.addKdoc(block = block)
}

public fun PropertyScope.addModifiers(modifiers: Iterable<KModifier>) {
  builder.addModifiers(modifiers = modifiers)
}

public fun PropertyScope.addModifiers(vararg modifiers: KModifier) {
  builder.addModifiers(modifiers = modifiers)
}

public fun PropertyScope.initializer(format: String, vararg args: Any?) {
  builder.initializer(format = format, args = args)
}

public fun PropertyScope.initializer(codeBlock: CodeBlock?) {
  builder.initializer(codeBlock = codeBlock)
}

public fun PropertySpec.Builder.applyDslBuilder(builder: PropertyScope.() -> Unit): PropertySpec.Builder = PropertyBuilder(this).apply(builder).builder

public fun PropertyScope.clearModifiers(): Unit = builder.modifiers.clear()

public fun PropertyScope.modifiers(vararg modifiers: KModifier) {
  clearModifiers()
  addModifiers(modifiers = modifiers)
}

public fun PropertyScope.modifiers(modifiers: Iterable<KModifier>) {
  clearModifiers()
  addModifiers(modifiers = modifiers)
}

public fun PropertyScope.modifier(modifier: KModifier): Unit = modifiers(modifier)

@Deprecated(
  message = "In Elucidator, we use the `addAnnotation` function to create new annotations. Use the `annotate` function instead.",
  replaceWith = ReplaceWith("annotate(annotationSpec)"),
  level = DeprecationLevel.WARNING,
)
public fun PropertyScope.addAnnotation(annotationSpec: AnnotationSpec) {
  builder.addAnnotation(annotationSpec = annotationSpec)
}

@Deprecated(
  message = "In Elucidator, we use the `addAnnotation` function to create new annotations. Use the `annotate` function instead.",
  replaceWith = ReplaceWith("annotate(annotationSpecs)"),
  level = DeprecationLevel.WARNING,
)
public fun PropertyScope.addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) {
  builder.addAnnotations(annotationSpecs = annotationSpecs)
}

public inline fun <reified T : Annotation> PropertyScope.annotate(noinline block: AnnotationScope.() -> Unit) {
  annotation(type = T::class, block = block).let(this::annotate)
}

public fun PropertyScope.annotate(annotationSpec: AnnotationSpec) {
  builder.addAnnotation(annotationSpec = annotationSpec)
}

public fun PropertyScope.annotate(annotationSpecs: Iterable<AnnotationSpec>) {
  builder.addAnnotations(annotationSpecs = annotationSpecs)
}

public fun PropertyScope.annotate(vararg annotationSpecs: AnnotationSpec) {
  builder.addAnnotations(annotationSpecs = annotationSpecs.toList())
}

public fun PropertyScope.annotate(`annotation`: ClassName) {
  builder.addAnnotation(annotation = annotation)
}

public inline fun <reified T> PropertyScope.annotate() {
  builder.addAnnotation(annotation = T::class)
}
