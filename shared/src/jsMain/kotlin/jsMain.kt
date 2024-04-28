import app.cash.sqldelight.db.SqlDriver
import com.den3000.kmpwaystoaurora.Database
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext
import app.cash.sqldelight.driver.sqljs.initSqlDriver
import kotlinx.coroutines.await

actual fun platform() = "Shared JVM"

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

actual fun getExecutionContext() = (Dispatchers.Default as CoroutineContext)

actual fun getCallbackContext() = (Dispatchers.Default as CoroutineContext)

actual fun getHttpRequestClient() : HttpClient? = HttpClient(Js)

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DriverFactory {
    actual suspend fun createDriver(): SqlDriver? {
        return initSqlDriver(Database.Schema).await()
    }
}