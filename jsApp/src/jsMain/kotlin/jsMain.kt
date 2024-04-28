import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun main() {
    std()
    serialization()
//    coroutines()
//    ktor()
    db()
//    test1()
    test2()
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
    // TODO: Finish wait loop with promises
    println("\n=== COROUTINES ===\n")

    triggerCoroutine(1000) { text, finished ->
        println("text: $text finished: $finished")
    }
}

fun ktor() {
    // TODO: Finish wait loop with promises
    println("\n=== KTOR ===\n")

    getKtorIoWelcomePageAsText { text, _ ->
        println(text)
    }
}

fun db() {
    // TODO: Finish wait loop with promises
    println("\n=== DB ===\n")

    val df = DriverFactory()
    getProgrammersFromSqlDelight(df) {
        println(it)
    }
}

fun test1() {
    // TODO: Finish wait loop with promises
    println("\n=== TEST 1 ===\n")

    val totalTime = MutableStateFlow<Long>(0)

    val start = getTimeMark()
    val df = DriverFactory()
    runTestOne(df) { text, _ ->
        totalTime.update { getDiffMs(start) }
        println("Time spent: ${totalTime.value} ms\n" + text.take(40))
    }
}

fun test2() {
    // TODO: Finish wait loop with promises
    println("\n=== TEST 2 ===\n")

    val totalTime = MutableStateFlow<Long>(0)

    val start = MutableStateFlow(getTimeMark())
    val df = DriverFactory()
    runTestTwo(df, started = {
        start.update { getTimeMark() }
    }) { text, _ ->
        totalTime.update { getDiffMs(start.value) }
        println("Time spent: ${totalTime.value} ms\n" + text.take(40))
    }
}