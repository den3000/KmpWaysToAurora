import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import com.den3000.kmpwaystoaurora.Database
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.dom.Worker
import kotlin.coroutines.CoroutineContext

actual fun platform() = "Shared JS"

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
        // NODE JS
//      // val worker = Worker(js("new URL('file://' + require('path').resolve(__dirname, '../../../node_modules/@cashapp/sqldelight-sqljs-worker/sqljs.worker.js'));") as String)

        // Browser JS
        val worker = Worker(js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)"""))
        val driver = WebWorkerDriver(worker)
        Database.Schema.awaitCreate(driver)
        return driver
    }
}

fun main() {
    println("JS SHARED")
}