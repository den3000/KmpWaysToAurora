import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import com.den3000.kmpwaystoaurora.Database
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.dom.Worker
import kotlin.coroutines.CoroutineContext

val scope = MainScope()

//class DbHelper(private val driverFactory: DriverFactory) {
//    private val db: Database? = null
//
//    private val mutex = Mutex()
//
//    private suspend fun createDb(driverFactory: DriverFactory): Database {
//        return Database(driverFactory.createDriver())
//    }
//}
suspend fun main() {

    println("JS SHARED STARTED")
    println("JS SHARED SWITCHED")
    val mutex = Mutex()
    mutex.withLock {
        val worker = Worker(js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""") as String)
        val driver = WebWorkerDriver(worker)
    }

//        .also { Database.Schema.create(it).await() }
//    driver.close()
    println("JS SHARED FINISHED")
}
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
        // Browser JS
        val worker = Worker(js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""") as String)

        // NODE JS
//      // val worker = Worker(js("new URL('file://' + require('path').resolve(__dirname, '../../../node_modules/@cashapp/sqldelight-sqljs-worker/sqljs.worker.js'));") as String)

        val driver = WebWorkerDriver(worker)
//            .also { Database.Schema.create(it).await() }
        Database.Schema.awaitCreate(driver)
        return null
    }

//    actual suspend fun createDriver(): SqlDriver? {
//        return initSqlDriver(Database.Schema).await()
//    }
}