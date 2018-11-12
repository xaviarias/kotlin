// TARGET_BACKEND: JVM
// IGNORE_BACKEND: JVM_IR
// FULL_JDK
// SKIP_JDK6
// WITH_RUNTIME

import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType

open class Base
class Derived : Base() {
    override fun toString() = "!"
}

class C {
    fun foo(s: String, d: Double?, x: Base): String = "$s$d$x"
}

fun box(): String {
    val mh = MethodHandles.lookup().findVirtual(
        C::class.java, "foo",
        MethodType.methodType(String::class.java, String::class.java, Double::class.javaObjectType, Base::class.java)
    )
    val result: String = mh.invoke(C(), "Hello", 0.01, Derived()) as String
    return if (result == "Hello0.01!") "OK" else "Fail: $result"
}
