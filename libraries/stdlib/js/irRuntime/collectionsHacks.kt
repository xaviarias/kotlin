/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package kotlin.collections

import kotlin.js.*

// Copied from libraries/stdlib/js/src/kotlin/collections/utils.kt
// Current inliner doesn't rename symbols inside `js` fun
@Suppress("UNUSED_PARAMETER")
internal fun deleteProperty(obj: Any, property: Any) {
    js("delete obj[property]")
}

internal fun arrayToString(array: Array<*>) = array.map { toString(it) }.joinToString(", ", "[", "]")

internal infix fun <T> Array<out T>.contentDeepEqualsInternal(other: Array<out T>) = contentDeepEqualsImpl(other)

internal fun <T> Array<out T>.contentDeepHashCodeInternal() = contentDeepHashCodeImpl()

internal fun <T> Array<out T>.contentDeepToStringInternal() = contentDeepToStringImpl()

internal fun <T> T.contentEqualsInternal(other: T): Boolean {
    val a = this.asDynamic()
    val b = other.asDynamic()

    if (a === b) return true

    if (!isArrayish(b) || a.length != b.length) return false

    for (i in 0 until a.length) {
        if (a[i] != b[i]) {
            return false
        }
    }
    return true
}

internal fun <T> Array<out T>.contentHashCodeInternal(): Int = fold(1) { a, v -> a * 31 + hashCode(v) }

internal fun <T> T.contentHashCodeInternal(): Int {
    val a = this.asDynamic()
    var result = 1

    for (i in 0 until a.length) {
        result = result * 31 + hashCode(a[i])
    }

    return result
}

internal fun <T> T.contentToStringInternal() = arrayToString(this as Array<*>)

internal fun <T> T.primitiveArraySortInternal(): Unit {
    this.asDynamic().sort(js(
        """
        function (a, b) {
             if (a < b) return -1;
             if (a > b) return 1;
             if (a === b) {
                if (a !== 0) return 0;
                var ia = 1 / a;
                return ia === 1 / b ? 0 : (ia < 0 ? -1 : 1);
             }
             return a !== a ? (b !== b ? 0 : 1) : -1
         }
         """
    ))
}