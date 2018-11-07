/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.quickfix

import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker

internal fun MutableList<KotlinType>.sortSubtypesFirst(): List<KotlinType> {
    val typeChecker = KotlinTypeChecker.DEFAULT
    sortWith(Comparator { a, b ->
        if (typeChecker.isSubtypeOf(a, b)) -1 else 1
    })
    return this
}
