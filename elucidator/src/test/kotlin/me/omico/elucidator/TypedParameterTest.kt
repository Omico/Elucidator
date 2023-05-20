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

import com.squareup.kotlinpoet.STAR
import kotlin.reflect.KClass
import kotlin.test.Test
import kotlin.test.assertEquals

class TypedParameterTest {

    @Test
    fun test() {
        val function = function("function") {
            addParameter("generics" with KClass::class.parameterizedBy(STAR))
            addParameter("array" with String::class nullable true defaultValue "arrayOf(\"default\")" vararg true)
        }
        assertEquals(
            expected = """
            |public fun function(generics: kotlin.reflect.KClass<*>, vararg array: kotlin.String? = arrayOf("default")): kotlin.Unit {
            |}
            |
            """.trimMargin(),
            actual = function.toString(),
        )
    }
}
