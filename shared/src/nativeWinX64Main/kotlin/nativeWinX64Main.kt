import kotlinx.cinterop.CFunction
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.invoke
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

// TODO: Should be shared somehow between native targets
@OptIn(ExperimentalForeignApi::class)
fun cfptrToFunc0(cfptr: CPointer<CFunction<(COpaquePointer) -> Unit>>, data: COpaquePointer): () -> Unit {
    return {
        cfptr.invoke(data)
    }
}