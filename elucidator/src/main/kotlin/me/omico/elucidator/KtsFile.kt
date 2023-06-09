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

import com.squareup.kotlinpoet.FileSpec

public fun ktsFile(
    fileName: String,
    packageName: String = "",
    block: KtFileScope.() -> Unit,
): FileSpec =
    FileSpec.scriptBuilder(fileName = fileName, packageName = packageName).applyDslBuilder(block).build()

public fun KtFileScope.addCallExpression(format: String, vararg args: Any, block: KtFileScope.() -> Unit) {
    require(builder.isScript) { "CallExpression can only be added to a script file." }
    builder.beginControlFlow(controlFlow = format, args = args)
    block()
    builder.endControlFlow()
}
