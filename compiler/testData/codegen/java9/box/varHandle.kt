// TARGET_BACKEND: JVM
// IGNORE_BACKEND: JVM_IR
// FULL_JDK
// SKIP_JDK6
// WITH_RUNTIME

import java.lang.invoke.MethodHandles
import kotlin.concurrent.thread

fun box(): String {
    val handle = MethodHandles.arrayElementVarHandle(ByteArray::class.java)
    val array = ByteArray(10)

    thread {
        Thread.sleep(400L)
        handle.setVolatile(array, 0, 42.toByte())
    }

    while (!handle.compareAndSet(array, 0, 42.toByte(), (-74).toByte())) {
        Thread.sleep(10L)
    }

    return if (handle.getVolatile(array, 0) == (-74).toByte()) "OK" else "Fail"
}

fun main() {
    box().let { if (it != "OK") throw AssertionError(it) }
}
