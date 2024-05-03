package foo;

import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;

public class Jtn {
    public static void main (String[] args) {

    }

    @CEntryPoint(name = "platform")
    private static CCharPointer platform(IsolateThread thread) {
        String result = foo.JvmMainKt.platform();
        return CTypeConversion.toCString(result).get();
    }
}
