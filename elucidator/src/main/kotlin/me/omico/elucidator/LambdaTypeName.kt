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

import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName

public fun lambdaTypeName(
    receiver: TypeName? = null,
    parameters: List<ParameterSpec> = emptyList(),
    returnType: TypeName,
): LambdaTypeName =
    LambdaTypeName.get(
        receiver = receiver,
        parameters = parameters,
        returnType = returnType,
    )

public fun lambdaTypeName(
    receiver: TypeName? = null,
    vararg parameters: TypeName = emptyArray(),
    returnType: TypeName,
): LambdaTypeName =
    LambdaTypeName.get(
        receiver = receiver,
        parameters = parameters,
        returnType = returnType,
    )
