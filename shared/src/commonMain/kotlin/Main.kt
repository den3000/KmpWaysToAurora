data class DataClass(
    val int: Int,
    val string: String,
)
expect fun platform(): String

expect fun getDataClass(): DataClass