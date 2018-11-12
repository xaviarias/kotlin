// TARGET_BACKEND: JVM
// IGNORE_BACKEND: JVM_IR
// FULL_JDK
// SKIP_JDK6
// WITH_REFLECT

import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import kotlin.reflect.jvm.javaMethod

inline class Z(val s: String)

fun foo(z: Z): String = z.s

fun box(): String {
    val mh = MethodHandles.lookup().findStatic(
        Class.forName("InvokeExactWithInlineClassKt"), ::foo.javaMethod!!.name,
        MethodType.methodType(String::class.java, String::class.java)
    )

    // TODO: should throw
    val r1 = mh.invokeExact(Z("OK")) as String
    if (r1 != "OK") return "Fail r1: $r1"

    return mh.invokeExact("OK") as String
}
