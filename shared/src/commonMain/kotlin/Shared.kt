import com.den3000.kmpwaystoaurora.Database
import com.den3000.kmpwaystoaurora.ProgrammerQueries
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import spacexlaunches.Api
import spacexlaunches.Db
import spacexlaunches.RocketLaunch

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
                callback("NO HttpClient AVAILABLE", true)
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

fun getProgrammersFromSqlDelight(driverFactory: DriverFactory, callback: suspend (String) -> Unit) {
    val scope = CoroutineScope(getExecutionContext())
    scope.launch {
        val driver = driverFactory.createDriver()
//            ?: run {
//            callback("NO NativeSqliteDriver AVAILABLE")
//            return@launch
//        }
//        val database = Database(driver)
//        val programmerQueries: ProgrammerQueries = database.programmerQueries
//        val str = programmerQueries.selectAll().executeAsList().joinToString(separator = "\n")
//        driver.close()
//        callback(str)
        callback("YEP")
    }
}

fun getTimeMark() = Clock.System.now()

fun getDiffMs(mark: Instant) = (Clock.System.now() - mark).inWholeMilliseconds

fun runTestOne(driverFactory: DriverFactory, callback: suspend (String, Boolean) -> Unit) {
    val scope = CoroutineScope(getExecutionContext())
    scope.launch {
        var attempts = 10
        while (attempts > 0) {
            val api = Api()
            val db = Db(driverFactory).apply { start() }
            val newLaunches = api.getAllLaunches()
            db.clearAndCreateLaunches(newLaunches)
            val cachedLaunches = db.getAllLaunches()
            val str = cachedLaunches.joinToString("\n") {
                "#: ${it.flightNumber} ${it.missionName} success: ${it.launchSuccess}"
            }

            withContext(getCallbackContext()) {
                callback("Attempt: $attempts\n$str", false)
            }

            attempts--
        }

        withContext(getCallbackContext()) {
            callback("", true)
        }
    }
}

fun runTestTwo(
    driverFactory: DriverFactory,
    started: () -> Unit,
    callback: suspend (String, Boolean) -> Unit
) {
    val scope = CoroutineScope(getExecutionContext())
    scope.launch {
        val api = Api()
        val db = Db(driverFactory)
        val allLaunchesJsonText = api.getAllLaunchesJsonText()
        val json = Json {
            ignoreUnknownKeys = true
            useAlternativeNames = false
        }

        started()

        var attempts = 100
        while (attempts > 0) {
            val newLaunches = json.decodeFromString<List<RocketLaunch>>(allLaunchesJsonText)

            db.clearAndCreateLaunches(newLaunches)
            val cachedLaunches = db.getAllLaunches()
            val str = cachedLaunches.joinToString("\n") {
                "#: ${it.flightNumber} ${it.missionName} success: ${it.launchSuccess}"
            }

            withContext(getCallbackContext()) {
                callback("Attempt: $attempts\n$str", false)
            }

            attempts--
        }

        api.close()
        withContext(getCallbackContext()) {
            callback("", true)
        }
    }
}