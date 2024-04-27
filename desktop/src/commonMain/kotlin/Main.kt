import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun main() {
    std()
    serialization()
    coroutines()
    ktor()
    db()
    test1()
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

    var fromLambda = ""
    triggerLambda {
        fromLambda = "Triggered from lambda on desktop"
    }

    println("Hello, $ktText\n$ktDataClass1Str\n" +
            "DataClass2\n" +
            "int: $ktDataClass2Int\n" +
            "string: $ktDataClass2Str\n" +
            "fromLambda: $fromLambda"
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

fun coroutines() {
    println("\n=== COROUTINES ===\n")

    val flowEnd = MutableStateFlow(false)
    triggerCoroutine(1000) { text, finished ->
        println("text: $text finished: $finished")
        flowEnd.update { finished }
    }
    while (!flowEnd.value) { /* wait loop */ }
}

fun ktor() {
    println("\n=== KTOR ===\n")

    val flowEnd = MutableStateFlow(false)
    getKtorIoWelcomePageAsText { text, finished ->
        println(text)
        flowEnd.update { finished }
    }
    while (!flowEnd.value) { /* wait loop */ }
}

fun db() {
    println("\n=== DB ===\n")

    val df = DriverFactory()
    println(getProgrammersFromSqlDelight(df))
}

fun test1() {
    println("\n=== TEST 1 ===\n")

    val flowEnd = MutableStateFlow(false)
    val df = DriverFactory()
    getLaunchesRaw(df) { text, finished ->
        println(text)
        flowEnd.update { finished }
    }
    while (!flowEnd.value) { /* wait loop */ }
}

fun test2() {

}