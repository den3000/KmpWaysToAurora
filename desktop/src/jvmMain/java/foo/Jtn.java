package foo;

import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.function.CFunctionPointer;
import org.graalvm.nativeimage.c.function.InvokeCFunctionPointer;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;

interface IStringCallback extends CFunctionPointer {
    @InvokeCFunctionPointer
    int invoke(CCharPointer str);
}

public class Jtn {
    public static void main (String[] args) {

    }

    @CEntryPoint(name = "platform")
    private static CCharPointer platform(IsolateThread thread) {
        String result = foo.JvmMainKt.platform();
        return CTypeConversion.toCString(result).get();
    }

    @CEntryPoint(name = "test2")
    private static void test2(IsolateThread thread, IStringCallback callback) {
        callback.invoke(CTypeConversion.toCString("FFFFFFFFFFFFFFFUCK").get());
    }
}
