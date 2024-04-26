import app.cash.sqldelight.db.SqlDriver
import io.ktor.client.HttpClient
import kotlin.coroutines.CoroutineContext

expect fun platform(): String

expect fun getDataClass(): DataClass

expect fun serializeToString(dc: DataClass): String

expect fun deserializeFromString(str: String): DataClass

expect fun getExecutionContext(): CoroutineContext

expect fun getCallbackContext(): CoroutineContext

expect fun getHttpRequestClient() : HttpClient?

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class DriverFactory() {
    fun createDriver(): SqlDriver?
}