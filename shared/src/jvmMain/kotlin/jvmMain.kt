import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

actual fun platform() = "Shared JVM"

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