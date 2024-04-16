actual fun platform() = "Shared Android"

actual fun getDataClass(): DataClass {
    return DataClass(
        int = 10,
        string = "some shared string"
    )
}