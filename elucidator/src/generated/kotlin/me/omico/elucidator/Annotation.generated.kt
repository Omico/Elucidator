//
// Generated by DslGenerator from Elucidator. Do not edit directly!!!
//
package me.omico.elucidator

import com.squareup.kotlinpoet.AnnotationSpec
import kotlin.Any
import kotlin.String
import kotlin.Unit

@ElucidatorDsl
public interface AnnotationScope {
  public val builder: AnnotationSpec.Builder

  public fun build(): AnnotationSpec
}

internal class AnnotationBuilder(
  override val builder: AnnotationSpec.Builder,
) : AnnotationScope {
  override fun build(): AnnotationSpec = builder.build()
}

public fun AnnotationScope.addMember(format: String, vararg args: Any) {
  builder.addMember(format = format, args = args)
}

public fun AnnotationSpec.Builder.applyDslBuilder(builder: AnnotationScope.() -> Unit):
    AnnotationSpec.Builder = AnnotationBuilder(this).apply(builder).builder