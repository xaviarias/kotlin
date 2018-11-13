/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.core

import com.intellij.psi.PsiClass
import com.intellij.psi.SyntheticElement
import com.intellij.psi.impl.light.LightElement
import com.intellij.psi.search.PsiShortNamesCache
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.idea.caches.resolve.util.javaResolutionFacade
import org.jetbrains.kotlin.load.java.structure.impl.JavaClassImpl
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.resolve.jvm.JavaDescriptorResolver

/*
 * The method is used for getting android resource light-classes from indexes.
 * An ordinal resolve can't be used for them because of currently it's impossible to get module information for such classes.
 */
@Suppress("unused") // Used from Android Studio 3.3 and 3.4
fun findAndroidResourceClasses(expression: KtExpression): List<ClassDescriptor> {
    val resolveScope = expression.containingFile.resolveScope
    val classesByName = PsiShortNamesCache.getInstance(expression.project).getClassesByName("R", resolveScope)

    val probablyRClasses = classesByName.filter { klass: PsiClass ->
        isAndroidSyntheticClass(klass)
    }

    if (probablyRClasses.isEmpty()) {
        return emptyList()
    }

    val javaResolutionFacade = expression.javaResolutionFacade() ?: return emptyList()
    val javaDescriptorResolver =
        javaResolutionFacade.tryGetFrontendService(expression, JavaDescriptorResolver::class.java) ?: return emptyList()

    return probablyRClasses.mapNotNull { klass ->
        javaDescriptorResolver.resolveClass(JavaClassImpl(klass))
    }
}

fun isAndroidSyntheticClass(psiClass: PsiClass): Boolean {
    // TODO: provide a better way for such classes identification
    return psiClass.containingFile?.originalFile?.virtualFile == null && psiClass is SyntheticElement && psiClass is LightElement
}