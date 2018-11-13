/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

@file:Suppress("unused", "UNUSED_PARAMETER")

package kotlin.collections

@library("arrayToString")
internal fun arrayToString(array: Array<*>): String = definedExternally

@library("arrayDeepEquals")
internal infix fun <T> Array<out T>.contentDeepEqualsInternal(other: Array<out T>): Boolean = definedExternally

@library("arrayDeepHashCode")
internal fun <T> Array<out T>.contentDeepHashCodeInternal(): Int = definedExternally

@library("arrayDeepToString")
internal fun <T> Array<out T>.contentDeepToStringInternal(): String = definedExternally

@library("arrayEquals")
internal fun <T> T.contentEqualsInternal(other: T): Boolean = definedExternally

@library("arrayHashCode")
internal fun <T> T.contentHashCodeInternal(): Int = definedExternally

@library("arrayToString")
internal fun <T> T.contentToStringInternal(): String = definedExternally

@library("primitiveArraySort")
internal fun <T> T.primitiveArraySortInternal(): Unit = definedExternally