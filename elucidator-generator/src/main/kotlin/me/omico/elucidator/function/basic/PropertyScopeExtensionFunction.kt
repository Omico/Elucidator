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
package me.omico.elucidator.function.basic

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.asTypeName
import me.omico.elucidator.nullable
import me.omico.elucidator.vararg
import me.omico.elucidator.with

internal val BasicExtensionFunctions_PropertyScope: List<BasicExtensionFunction> by lazy {
    buildList {
        addAll(BasicExtensionFunctions_addKdoc)
        addAll(BasicExtensionFunctions_addModifiers)
        addAll(BasicExtensionFunctions_initializer)
    }
}

private val BasicExtensionFunctions_initializer: List<BasicExtensionFunction> = listOf(
    BasicExtensionFunction(
        name = "initializer",
        parameters = arrayOf(
            "format" with String::class,
            "args" with Any::class.asTypeName().copy(nullable = true) vararg true,
        ),
    ),
    BasicExtensionFunction("initializer", "codeBlock" with CodeBlock::class nullable true),
)
