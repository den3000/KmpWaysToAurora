import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import app.cash.sqldelight.db.SqlDriver
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.cstr
import kotlinx.cinterop.invoke
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext

actual fun platform() = "Shared Linux Arm64"

actual fun getDataClass(): DataClass {
    return DataClass(
        int = 10,
        string = "some string"
    )
}

actual fun triggerLambda(callback: () -> Unit) {
    callback()
}

actual fun serializeToString(dc: DataClass): String {
    return Json.encodeToString(dc)
}

actual fun deserializeFromString(str: String): DataClass {
    return Json.decodeFromString(str)
}

actual fun getExecutionContext() = (Dispatchers.Default as CoroutineContext)

actual fun getCallbackContext() = (Dispatchers.Default as CoroutineContext)

actual fun getHttpRequestClient() : HttpClient? {
    return  null // HttpClient(CIO) - TLS sessions are not supported on Native platform
}

actual fun getKtorIoWelcomePageAsText(callback: suspend (String, Boolean) -> Unit) {
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        val client = getHttpRequestClient()
        val response = client?.get("https://ktor.io/docs/welcome.html")
        if (response != null) {
            response.bodyAsText().let {
                withContext(Dispatchers.Default) {
                    callback(it, true)
                }
                client.close()
            }
        } else {
            callback("TLS sessions are not supported on Native platform", true)
            client?.close()
        }
    }
}

actual class DriverFactory {
    actual fun createDriver(): SqlDriver? {
        println("NativeSqliteDriver not implemented for Linux Arm64")
        return null
    }
}

// TODO: Should be shared somehow between native targets

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
    cfptr: CPointer<CFunction<(COpaquePointer, CValuesRef<ByteVar>, Boolean) -> Unit>>,
    data: COpaquePointer
) {
    triggerCoroutine(delayInMs) { str, b ->
        cfptr.invoke(data, str.cstr, b)
    }
}

@OptIn(ExperimentalForeignApi::class)
fun getKtorIoWelcomePageAsTextCfptr(
    cfptr: CPointer<CFunction<(COpaquePointer, CValuesRef<ByteVar>, Boolean) -> Unit>>,
    data: COpaquePointer
) {
    getKtorIoWelcomePageAsText { s, b ->
        cfptr.invoke(data, s.cstr, b)
    }
}