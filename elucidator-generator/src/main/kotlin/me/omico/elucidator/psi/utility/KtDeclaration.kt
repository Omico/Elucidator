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
package me.omico.elucidator.psi.utility

import org.jetbrains.kotlin.idea.base.utils.fqname.fqName
import org.jetbrains.kotlin.idea.structuralsearch.resolveKotlinType
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.types.isNullable

internal val KtDeclaration.typeFqName: String
    get() = requireNotNull(resolveKotlinType()?.fqName?.asString())

internal val KtDeclaration.isNullable: Boolean
    get() = requireNotNull(resolveKotlinType()?.isNullable())
