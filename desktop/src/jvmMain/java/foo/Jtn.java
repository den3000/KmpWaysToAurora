package foo;

import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.c.function.CEntryPoint;

public class Jtn {
    public static void main (String[] args) {

    }

    @CEntryPoint(name = "platform")
    private static String platform(IsolateThread thread) {
        return foo.JvmMainKt.platform();
    }
}
