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
package me.omico.elucidator

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName

public inline fun <reified T> TypeScope.superclass() {
    builder.superclass(superclass = T::class)
}

public fun TypeScope.superclass(type: TypeName) {
    builder.superclass(superclass = type)
}

public fun TypeScope.primaryConstructor(primaryConstructor: FunSpec?) {
    builder.primaryConstructor(primaryConstructor = primaryConstructor)
}

public fun TypeScope.primaryConstructor(block: FunctionScope.() -> Unit): Unit =
    constructorFunction(block = block).let(::primaryConstructor)

public fun TypeScope.addSuperinterface(type: TypeName, delegate: CodeBlock = EmptyCodeBlock) {
    builder.addSuperinterface(superinterface = type, delegate = delegate)
}

public fun TypeScope.addInitializerBlock(block: CodeBlock) {
    builder.addInitializerBlock(block = block)
}

public fun TypeScope.addFunction(name: String, block: FunctionScope.() -> Unit): Unit =
    function(name = name, block = block).let(::addFunction)

public inline fun <reified T> TypeScope.addProperty(
    name: String,
    vararg modifiers: KModifier,
    noinline block: PropertyScope.() -> Unit = {},
): Unit =
    property<T>(name = name, modifiers = modifiers, block = block).let(::addProperty)

public fun TypeScope.addProperty(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    block: PropertyScope.() -> Unit = {},
): Unit =
    property(name = name, type = type, modifiers = modifiers, block = block).let(::addProperty)
