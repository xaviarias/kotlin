/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.codegen

import org.jetbrains.kotlin.spec.parsers.CommonParser
import org.jetbrains.kotlin.spec.parsers.CommonPatterns.packagePattern
import org.jetbrains.kotlin.spec.utils.GeneralConfiguration.TESTDATA_PATH
import org.jetbrains.kotlin.test.InTextDirectivesUtils
import java.io.File

abstract class AbstractBlackBoxCodegenTestSpec : AbstractBlackBoxCodegenTest() {
    companion object {
        private const val CODEGEN_BOX_TESTDATA_PATH = "$TESTDATA_PATH/codegen/box"
        private const val HELPERS_PATH = "$CODEGEN_BOX_TESTDATA_PATH/helpers"
        private const val HELPERS_PACKAGE_VARIABLE = "<!PACKAGE!>"
        private const val HELPERS_DIRECTIVE = "// HELPERS:"

        // map of pairs: source helper filename - target helper filename
        private val helperDirectives = mapOf(
            "REFLECT" to "reflect.kt"
        )
    }

    private fun addPackageDirectiveToHelperFile(helperContent: String, packageName: String?) =
        helperContent.replace(HELPERS_PACKAGE_VARIABLE, if (packageName == null) "" else "package $packageName")

    private fun includeHelpers(wholeFile: File, files: MutableList<TestFile>) {
        val fileContent = wholeFile.readText()
        val helpersSpecified = InTextDirectivesUtils.findListWithPrefixes(fileContent, HELPERS_DIRECTIVE)
        val packageName = packagePattern.matcher(fileContent).let {
            if (it.find()) it.group("packageName") else null
        }

        helpersSpecified.forEach {
            if (helperDirectives.contains(it)) {
                val helperContent = File("$HELPERS_PATH/${helperDirectives[it]}").readText()
                files.add(
                    TestFile(helperDirectives[it]!!, addPackageDirectiveToHelperFile(helperContent, packageName))
                )
            }
        }
    }

    override fun doMultiFileTest(wholeFile: File, files: MutableList<TestFile>, javaFilesDir: File?) {
        val (specTest, _) = CommonParser.parseSpecTest(wholeFile.canonicalPath, mapOf("main.kt" to wholeFile.readText()))

        println(specTest)

        includeHelpers(wholeFile, files)

        super.doMultiFileTest(wholeFile, files, javaFilesDir)
    }
}
