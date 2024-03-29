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

import java.nio.file.Path
import kotlin.io.path.createTempDirectory
import kotlin.io.path.readText
import kotlin.test.Test
import kotlin.test.assertEquals

class KtFileTest {
    private val tempDirectory: Path = createTempDirectory()

    @Test
    fun test() {
        ktFile("hello", "World") {
            addFunction("test") {
                addParameter<String>("parameter1")
                addParameter<Any>("parameter2")
                returnType<Unit>()
            }
            writeTo(tempDirectory)
        }
        val expected =
            """
            |package hello
            |
            |import kotlin.Any
            |import kotlin.String
            |
            |public fun test(parameter1: String, parameter2: Any) {
            |}
            |
            """.trimMargin()
        assertEquals(
            expected = expected,
            actual = tempDirectory.resolve("hello/World.kt").readText(),
        )
    }
}
