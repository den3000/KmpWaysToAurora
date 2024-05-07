package com.den3000.kmpwaystoaurora.shared

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.cstr
import kotlinx.cinterop.invoke
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalForeignApi::class)
fun triggerLambdaCfptr(
    cfptr: CPointer<CFunction<(COpaquePointer) -> Unit>>,
    data: COpaquePointer
) {
    triggerLambda {
        cfptr.invoke(data)
    }
}

@OptIn(ExperimentalForeignApi::class)
fun triggerCoroutineCfptr(
    delayInMs: Long,
    cfptr: CPointer<CFunction<(COpaquePointer, CValuesRef<ByteVar>) -> Unit>>,
    data: COpaquePointer
) {
    val scope = CoroutineScope(getExecutionContext())
    scope.launch {
        val result = triggerCoroutine(delayInMs)
        cfptr.invoke(data, result.cstr)
    }
}

@OptIn(ExperimentalForeignApi::class)
fun triggerFlowCfptr(
    delayInMs: Long,
    cfptr: CPointer<CFunction<(COpaquePointer, CValuesRef<ByteVar>) -> Unit>>,
    data: COpaquePointer
) {
    val scope = CoroutineScope(getExecutionContext())
    scope.launch {
        triggerFlow(delayInMs)
            .collect { result ->
                cfptr.invoke(data, result.cstr)
            }
    }
}

@OptIn(ExperimentalForeignApi::class)
fun getKtorIoWelcomePageAsTextCfptr(
    cfptr: CPointer<CFunction<(COpaquePointer, CValuesRef<ByteVar>) -> Unit>>,
    data: COpaquePointer
) {
    val scope = CoroutineScope(getExecutionContext())
    scope.launch {
        val result = getKtorIoWelcomePageAsText()
        cfptr.invoke(data, result.cstr)
    }
}

@OptIn(ExperimentalForeignApi::class)
fun getProgrammersFromSqlDelightCfptr(
    driverFactory: DriverFactory,
    cfptr: CPointer<CFunction<(COpaquePointer, CValuesRef<ByteVar>) -> Unit>>,
    data: COpaquePointer
) {
    val scope = CoroutineScope(getExecutionContext())
    scope.launch {
        getProgrammersFromSqlDelight(driverFactory)
            .collect { result ->
                cfptr.invoke(data, result.cstr)
            }
    }
}

@OptIn(ExperimentalForeignApi::class)
fun runTestOneCfptr(
    driverFactory: DriverFactory,
    cfptr: CPointer<CFunction<(COpaquePointer, CValuesRef<ByteVar>) -> Unit>>,
    data: COpaquePointer
) {
    val scope = CoroutineScope(getExecutionContext())
    scope.launch {
        runTestOne(driverFactory)
            .collect { result ->
                cfptr.invoke(data, result.cstr)
            }
    }
}

@OptIn(ExperimentalForeignApi::class)
fun runTestTwoCfptr(
    driverFactory: DriverFactory,
    startedCfptr: CPointer<CFunction<(COpaquePointer) -> Unit>>,
    callbackCfptr: CPointer<CFunction<(COpaquePointer, CValuesRef<ByteVar>) -> Unit>>,
    data: COpaquePointer
) {
    val scope = CoroutineScope(getExecutionContext())
    scope.launch {
        runTestTwo(driverFactory) {
            startedCfptr.invoke(data)
        }.collect { result ->
            callbackCfptr.invoke(data, result.cstr)
        }
    }
}