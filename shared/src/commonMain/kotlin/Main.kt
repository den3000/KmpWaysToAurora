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

expect fun triggerCoroutine(delayInMs: Long, callback: (String, Boolean) -> Unit)

expect fun getHttpRequestClient() : HttpClient?

expect fun getKtorIoWelcomePageAsText(callback: (String, Boolean) -> Unit)

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): Database {
    val driver = driverFactory.createDriver()
    val database = Database(driver)

    // Do more work with the database (see below).
    return database
}