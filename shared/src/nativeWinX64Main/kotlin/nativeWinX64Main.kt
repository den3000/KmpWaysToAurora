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

actual fun platform() = "Shared WIN X64"

actual fun triggerLambda(callback: () -> Unit) {
    callback()
}

actual fun getDataClass(): DataClass {
    return DataClass(
        int = 10,
        string = "some string"
    )
}

actual fun serializeToString(dc: DataClass): String {
    return Json.encodeToString(dc)
}

actual fun deserializeFromString(str: String): DataClass {
    return Json.decodeFromString(str)
}

actual fun triggerCoroutine(delayInMs: Long, callback: (String, Boolean) -> Unit) {
    val scope = CoroutineScope(Dispatchers.Default)
    var max = 3
    scope.launch {
        while (max > 0) {
            withContext(Dispatchers.Default) {
                callback("$max", false)
            }

            max -= 1
            delay(delayInMs)
        }

        withContext(Dispatchers.Default) {
            callback("Kotlin Coroutines World!", true)
        }
    }
}

// TODO: Should be shared somehow between native targets
@OptIn(ExperimentalForeignApi::class)
fun cfptrToFunc0(cfptr: CPointer<CFunction<(COpaquePointer) -> Unit>>, data: COpaquePointer): () -> Unit {
    return {
        cfptr.invoke(data)
    }
}

@OptIn(ExperimentalForeignApi::class)
fun cfptrToFunc2(cfptr: CPointer<CFunction<(COpaquePointer, CValuesRef<ByteVar>, Boolean) -> Unit>>, data: COpaquePointer): (String, Boolean) -> Unit {
    return { str, b ->
        cfptr.invoke(data, str.cstr, b)
    }
}