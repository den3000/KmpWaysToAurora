import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class DataClass(
    val int: Int,
    val string: String,
)
expect fun platform(): String

expect fun getDataClass(): DataClass

expect fun serializeToString(dc: DataClass): String

expect fun deserializeFromString(str: String): DataClass