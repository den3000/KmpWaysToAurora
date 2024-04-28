import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.den3000.kmpwaystoaurora.Database
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext

actual fun platform() = "Shared Android"

actual fun getDataClass(): DataClass {
    return DataClass(
        int = 10,
        string = "some shared string"
    )
}

actual fun serializeToString(dc: DataClass): String {
    return Json.encodeToString(dc)
}

actual fun deserializeFromString(str: String): DataClass {
    return Json.decodeFromString(str)
}

actual fun getExecutionContext() = (Dispatchers.IO as CoroutineContext)

actual fun getCallbackContext() = (Dispatchers.Main as CoroutineContext)

actual fun getHttpRequestClient() : HttpClient? = HttpClient(OkHttp)

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DriverFactory(private val context: Context?) {
    actual constructor() : this(null)

    actual suspend fun createDriver(): SqlDriver? {
        return if (context != null) {
            AndroidSqliteDriver(Database.Schema, context, "Database.db")
        } else {
            null
        }
    }
}