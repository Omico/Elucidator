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

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import me.omico.elucidator.GeneratedType
import me.omico.elucidator.KtFileScope
import me.omico.elucidator.addFunction
import me.omico.elucidator.addInterface
import me.omico.elucidator.addProperty
import me.omico.elucidator.modifier
import me.omico.elucidator.returnType

internal fun KtFileScope.addDslScopeInterface(type: GeneratedType): Unit =
    addInterface(type.generatedScopeName) {
        // TODO Fix addAnnotation
        builder.addAnnotation(ClassName("me.omico.elucidator", "ElucidatorDsl"))
        addProperty("builder", type.builderClassName)
        addFunction("build") {
            modifier(KModifier.ABSTRACT)
            returnType(type.objectClassName)
        }
    }
