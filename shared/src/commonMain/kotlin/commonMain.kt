import app.cash.sqldelight.db.SqlDriver
import com.den3000.kmpwaystoaurora.Database
import com.den3000.kmpwaystoaurora.ProgrammerQueries
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlin.coroutines.CoroutineContext

@Serializable
data class DataClass(
    val int: Int,
    val string: String,
)
expect fun platform(): String

expect fun getDataClass(): DataClass

expect fun triggerLambda(callback: () -> Unit)

expect fun serializeToString(dc: DataClass): String

expect fun deserializeFromString(str: String): DataClass

expect fun getExecutionContext(): CoroutineContext

expect fun getCallbackContext(): CoroutineContext

fun triggerCoroutine(delayInMs: Long, callback: suspend (String, Boolean) -> Unit) {
    val scope = CoroutineScope(getExecutionContext())
    var max = 3
    scope.launch {
        while (max > 0) {
            withContext(getCallbackContext()) {
                callback("$max", false)
            }

            max -= 1
            delay(delayInMs)
        }

        withContext(getCallbackContext()) {
            callback("Kotlin Coroutines World!", true)
        }
    }
}

expect fun getHttpRequestClient() : HttpClient?

expect fun getKtorIoWelcomePageAsText(callback: suspend (String, Boolean) -> Unit)

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class DriverFactory() {
    fun createDriver(): SqlDriver?
}

fun getProgrammersFromSqlDelight(driverFactory: DriverFactory): String {
    val driver = driverFactory.createDriver() ?: return "FAILED TO CREATE DRIVER"
    val database = Database(driver)
    val programmerQueries: ProgrammerQueries = database.programmerQueries
    val str = programmerQueries.selectAll().executeAsList().joinToString(separator = "\n")
    driver.close()
    return str
}