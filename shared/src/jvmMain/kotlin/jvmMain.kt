import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.den3000.kmpwaystoaurora.Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

actual fun triggerCoroutine(delayInMs: Long, callback: (String, Boolean) -> Unit) {
    val scope = CoroutineScope(Dispatchers.IO)
    var max = 3
    scope.launch {
        while (max > 0) {
            withContext(Dispatchers.Main) {
                callback("$max", false)
            }

            max -= 1
            delay(delayInMs)
        }

        withContext(Dispatchers.Main) {
            callback("Kotlin Coroutines World!", true)
        }
    }
}

actual fun getHttpRequestClient() : HttpClient? {
    return  HttpClient(OkHttp)
}

actual fun getKtorIoWelcomePageAsText(callback: (String, Boolean) -> Unit) {
    val scope = CoroutineScope(Dispatchers.IO)
    scope.launch {
        val response = getHttpRequestClient()?.get("https://ktor.io/docs/welcome.html")
        response?.bodyAsText()?.let {
            withContext(Dispatchers.Main) {
                callback(it, true)
            }
        }
    }
}

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
        return driver
    }
}