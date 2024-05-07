package com.den3000.kmpwaystoaurora.shared

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asPromise
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Init fun for run after ready index.html
 */
@OptIn(ExperimentalJsExport::class)
@JsExport
fun init() {
    sendEvent("Init")
}

/**
 * NPM package for generate UUID
 */
@JsModule("uuid")
@JsNonModule
external object Uuid {
    fun v4(): String
}

@OptIn(ExperimentalJsExport::class)
@JsExport
fun stdJS(): String {
    val ktText = platform()
    val ktDataClass1 = getDataClass()
    val ktDataClass1Str = ktDataClass1.toString()

    val ktDataClass2 = DataClass(
        int = 2,
        string = "Some desktop string"
    )
    val ktDataClass2Int = ktDataClass2.int
    val ktDataClass2Str = ktDataClass2.string

    var fromLambda = ""
    triggerLambda {
        fromLambda = "Triggered from lambda on desktop"
    }

    return "Hello, $ktText\n$ktDataClass1Str\n" +
            "DataClass2\n" +
            "int: $ktDataClass2Int\n" +
            "string: $ktDataClass2Str\n" +
            "fromLambda: $fromLambda"
}

@OptIn(ExperimentalJsExport::class)
@JsExport
fun serializationJS(): String {
    var result = ""
    var dc = getDataClass()
    result += "Original to string: $dc\n"
    val str = serializeToString(dc)
    result += "Serialized: $str\n"
    dc = deserializeFromString(str)
    result += "Deserialized from string: $dc\n"

    return result
}

@OptIn(ExperimentalJsExport::class)
@JsExport
fun coroutineJS(): String {
    val scope = CoroutineScope(getExecutionContext())
    val caller = Uuid.v4()
    scope.async {
        triggerCoroutine(4000)
    }.asPromise().then {
        sendEventResponse(caller, response = it)
    }
    return caller
}

@OptIn(ExperimentalJsExport::class)
@JsExport
fun flowJS(): String {
    val scope = CoroutineScope(getExecutionContext())
    val caller = Uuid.v4()
    scope.launch {
        triggerFlow(2000)
            .collect { result ->
                sendEventResponse(caller, response = result)
            }
    }
    return caller
}

@OptIn(ExperimentalJsExport::class)
@JsExport
fun ktorJS(): Any {
    val scope = CoroutineScope(getExecutionContext())
    val caller = Uuid.v4()
    scope.async {
        getKtorIoWelcomePageAsText()
    }.asPromise().then {
        sendEventResponse(caller, response = it)
    }
    return caller
}

@OptIn(ExperimentalJsExport::class)
@JsExport
fun dbJS(): String {
    val scope = CoroutineScope(getExecutionContext())
    val caller = Uuid.v4()
    scope.launch {
        val df = DriverFactory()
        getProgrammersFromSqlDelight(df)
            .collect { result ->
                sendEventResponse(caller, response = result)
            }
    }
    return caller
}