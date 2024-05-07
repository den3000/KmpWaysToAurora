package com.den3000.kmpwaystoaurora.desktop;

import com.den3000.kmpwaystoaurora.shared.JvmMainKt;

import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.function.CFunctionPointer;
import org.graalvm.nativeimage.c.function.InvokeCFunctionPointer;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;
import org.graalvm.word.Pointer;
import org.jetbrains.annotations.NotNull;

interface IStringCallback extends CFunctionPointer {
    @InvokeCFunctionPointer
    void invoke(CCharPointer str, Pointer data);
}

public class Jtn {
    public static void main (String[] args) { }

    @CEntryPoint(name = "jtn_platform")
    private static CCharPointer platform(IsolateThread thread) {
        String result = JvmMainKt.platform();
        return CTypeConversion.toCString(result).get();
    }

    @CEntryPoint(name = "jtn_test2")
    private static void test2(IsolateThread thread, IStringCallback callback, Pointer data) {
        // Anonymous class is mandatory here, lambda wouldn't work with Graal
        JtnKt.test2_jtn(new TestTwoJtnCallback() {
            @Override
            public void invoke(@NotNull String str) {
                callback.invoke(CTypeConversion.toCString(str).get(), data);
            }
        });
    }
}
