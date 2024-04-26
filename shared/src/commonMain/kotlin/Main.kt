import app.cash.sqldelight.db.SqlDriver
import com.den3000.kmpwaystoaurora.Database
import com.den3000.kmpwaystoaurora.ProgrammerQueries
import io.ktor.client.HttpClient
import kotlinx.serialization.Serializable

@Serializable
data class DataClass(
    val int: Int,
    val string: String,
)
expect fun platform(): String

expect fun triggerLambda(callback: () -> Unit)

expect fun getDataClass(): DataClass

expect fun serializeToString(dc: DataClass): String

expect fun deserializeFromString(str: String): DataClass

expect fun triggerCoroutine(delayInMs: Long, callback: suspend (String, Boolean) -> Unit)

expect fun getHttpRequestClient() : HttpClient?

expect fun getKtorIoWelcomePageAsText(callback: suspend (String, Boolean) -> Unit)

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class DriverFactory {
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