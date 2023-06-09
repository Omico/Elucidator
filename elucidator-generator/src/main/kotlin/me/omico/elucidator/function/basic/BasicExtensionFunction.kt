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

import me.omico.elucidator.TypedParameter

internal data class BasicExtensionFunction(
    val name: String,
    val parameters: List<TypedParameter> = emptyList(),
)

internal typealias BasicExtensionFunctions = Map<String, List<BasicExtensionFunction>>

internal fun BasicExtensionFunction(name: String, vararg parameters: TypedParameter): BasicExtensionFunction =
    BasicExtensionFunction(name = name, parameters = parameters.toList())
