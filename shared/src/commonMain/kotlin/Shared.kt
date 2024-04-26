import com.den3000.kmpwaystoaurora.Database
import com.den3000.kmpwaystoaurora.ProgrammerQueries
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

@Serializable
data class DataClass(
    val int: Int,
    val string: String,
)

fun triggerLambda(callback: () -> Unit) {
    callback()
}

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

fun getKtorIoWelcomePageAsText(callback: suspend (String, Boolean) -> Unit) {
    val scope = CoroutineScope(getExecutionContext())
    scope.launch {
        val client = getHttpRequestClient() ?: run {
            withContext(getCallbackContext()) {
                callback("NO HTTP CLIENT AVAILABLE", true)
            }
            return@launch
        }

        val response = client.get("https://ktor.io/docs/welcome.html")
        response.bodyAsText().let {
            withContext(getCallbackContext()) {
                callback(it, true)
            }
            client.close()
        }
    }
}

fun getProgrammersFromSqlDelight(driverFactory: DriverFactory): String {
    val driver = driverFactory.createDriver() ?: return "FAILED TO CREATE DRIVER"
    val database = Database(driver)
    val programmerQueries: ProgrammerQueries = database.programmerQueries
    val str = programmerQueries.selectAll().executeAsList().joinToString(separator = "\n")
    driver.close()
    return str
}