fun main() {
    std()
    serialization()
}

fun std() {
    println("\n=== STD ===\n")

    val ktText = platform()
    val ktDataClass1 = getDataClass()
    val ktDataClass1Str = ktDataClass1.toString()

    val ktDataClass2 = DataClass(
        int = 2,
        string = "some android string"
    )
    val ktDataClass2Int = ktDataClass2.int
    val ktDataClass2Str = ktDataClass2.string

    val l = createLambda()
    triggerLambda(l)

    println("Hello, $ktText\n$ktDataClass1Str\n" +
            "DataClass2\n" +
            "int: $ktDataClass2Int\n" +
            "string: $ktDataClass2Str"
    )
}

fun serialization() {
    println("\n=== SERIALIZATION ===\n")

    var dc = getDataClass()
    println("Original to string: $dc")
    val str = serializeToString(dc)
    println("Serialized: $str")
    dc = deserializeFromString(str)
    println("Deserialized from string: $dc")
}