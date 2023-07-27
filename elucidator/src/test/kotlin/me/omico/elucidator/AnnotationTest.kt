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

import com.squareup.kotlinpoet.ClassName
import kotlin.test.Test
import kotlin.test.assertEquals

class AnnotationTest {
    @Test
    fun test() {
        val file = ktFile("hello", "World") {
            addAnnotation("AAnnotation")
            val bAnnotationClassName = ClassName("hello", "BAnnotation")
            val bAnnotation = bAnnotationClassName.asAnnotation { addMember("%S", "value") }
            addAnnotation("BAnnotation") {
                addProperty<String>("value")
            }
            addFunction("test") {
                annotate<Suppress> {
                    addMember("%S", "unused")
                }
                annotate(ClassName("hello", "AAnnotation"))
                annotate(bAnnotation)
            }
        }
        val expected =
            """
            |package hello
            |
            |import kotlin.String
            |import kotlin.Suppress
            |
            |public annotation class AAnnotation
            |
            |public annotation class BAnnotation {
            |  public val `value`: String
            |}
            |
            |@Suppress("unused")
            |@AAnnotation
            |@BAnnotation("value")
            |public fun test() {
            |}
            |
            """.trimMargin()
        assertEquals(expected = expected, actual = file.toString())
    }
}
