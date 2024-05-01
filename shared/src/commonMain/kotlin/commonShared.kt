import app.cash.sqldelight.async.coroutines.awaitAsList
import com.den3000.kmpwaystoaurora.Database
import com.den3000.kmpwaystoaurora.ProgrammerQueries
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

suspend fun triggerCoroutine(delayInMs: Long): String {
    delay(delayInMs)
    return "Coroutine finished"
}

suspend fun triggerFlow(delayInMs: Long): Flow<String> {
    return flow {
        var max = 3
        while (max > 0) {
            emit("Flow value: $max")

            max -= 1
            delay(delayInMs)
        }
    }
}

suspend fun getKtorIoWelcomePageAsText() : String {
        val client = getHttpRequestClient() ?: return "NO HttpClient AVAILABLE"
        val response = client.get("https://ktor.io/docs/welcome.html")
        val responseText = response.bodyAsText()
        client.close()
        return responseText
}

suspend fun getProgrammersFromSqlDelight(driverFactory: DriverFactory) : Flow<String> {
    return flow {
        val driver = driverFactory.createDriver() ?: run {
            emit("NO NativeSqliteDriver AVAILABLE")
            return@flow
        }

        val database = Database(driver)
        val programmerQueries: ProgrammerQueries = database.programmerQueries
        val list = programmerQueries.selectAll().awaitAsList()
        driver.close()
        list.forEach { emit(it.toString()) }
    }
}

fun getTimeMark() = Clock.System.now()

fun getDiffMs(mark: Instant) = (Clock.System.now() - mark).inWholeMilliseconds

suspend fun runTestOne(driverFactory: DriverFactory) : Flow<String> {
    return flow {
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

            api.close()
            emit("Attempt: $attempts\n$str")
            attempts--
        }
    }
}

// TODO: Ideally split into 2 functions
fun runTestTwo(
    driverFactory: DriverFactory,
    started: () -> Unit
): Flow<String> {
    return flow {
        val api = Api()
        val db = Db(driverFactory)
        val allLaunchesJsonText = api.getAllLaunchesJsonText()
        val json = Json {
            ignoreUnknownKeys = true
            useAlternativeNames = false
        }
        api.close()

        started()
        var attempts = 100
        while (attempts > 0) {
            val newLaunches = json.decodeFromString<List<RocketLaunch>>(allLaunchesJsonText)
            db.clearAndCreateLaunches(newLaunches)
            val cachedLaunches = db.getAllLaunches()
            val str = cachedLaunches.joinToString("\n") {
                "#: ${it.flightNumber} ${it.missionName} success: ${it.launchSuccess}"
            }
            emit("Attempt: $attempts\n$str")
            attempts--
        }
    }
}