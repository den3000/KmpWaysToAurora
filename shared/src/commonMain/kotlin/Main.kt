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