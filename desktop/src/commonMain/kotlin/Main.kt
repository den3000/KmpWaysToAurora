fun main() {
    std()
}

fun std() {
    println("\n\n\n=== STD ===")

    val ktText = platform()
    val ktDataClass1 = getDataClass()
    val ktDataClass1Str = ktDataClass1.toString()

    val ktDataClass2 = DataClass(
        int = 2,
        string = "some android string"
    )
    val ktDataClass2Int = ktDataClass2.int
    val ktDataClass2Str = ktDataClass2.string

    println("Hello, $ktText\n$ktDataClass1Str\n" +
            "DataClass2\n" +
            "int: $ktDataClass2Int\n" +
            "string: $ktDataClass2Str"
    )

}