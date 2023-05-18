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
package me.omico.elucidator.type

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import me.omico.elucidator.GeneratedType
import me.omico.elucidator.KtFileScope
import me.omico.elucidator.addFunction
import me.omico.elucidator.addProperty
import me.omico.elucidator.addType
import me.omico.elucidator.applyDslBuilder
import me.omico.elucidator.initializer
import me.omico.elucidator.modifier
import me.omico.elucidator.returnStatement

internal fun KtFileScope.addDslBuilderClass(type: GeneratedType): Unit =
    TypeSpec.classBuilder(type.generatedBuilderName)
        .addModifiers(KModifier.INTERNAL)
        .apply {
            FunSpec.constructorBuilder()
                .addParameter("builder", type.builderClassName)
                .build()
                .let(::primaryConstructor)
        }
        .applyDslBuilder {
            PropertySpec.builder("builder", type.builderClassName)
                .applyDslBuilder {
                    modifier(KModifier.OVERRIDE)
                    initializer("builder")
                }
                .build()
                .let(::addProperty)
        }
        .addSuperinterface(type.generatedScopeClassName)
        .applyDslBuilder {
            addFunction("build") {
                modifier(KModifier.OVERRIDE)
                returnStatement("builder.build()")
                builder.returns(type.objectClassName)
            }
        }
        .build()
        .let(::addType)
