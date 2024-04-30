import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun main() {
    std()
    serialization()
    coroutines()
    ktor()
    db()
    test1()
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

suspend fun coroutines() {
    println("\n=== COROUTINES ===\n")

    suspendCoroutine { continuation ->
        triggerCoroutine(1000) { text, finished ->
            println(println("text: $text finished: $finished"))
            if (finished) { continuation.resume(Unit) }
        }
    }
}

suspend fun ktor() {
    println("\n=== KTOR ===\n")

    suspendCoroutine { continuation ->
        getKtorIoWelcomePageAsText { text, finished ->
            println(text)
            if (finished) { continuation.resume(Unit) }
        }
    }
}

suspend fun db() {
    println("\n=== DB ===\n")

    suspendCoroutine { continuation ->
        val df = DriverFactory()
        getProgrammersFromSqlDelight(df) {
            println(it)
            continuation.resume(Unit)
        }
    }
}

suspend fun test1() {
    println("\n=== TEST 1 ===\n")

    suspendCoroutine { continuation ->
        val totalTime = MutableStateFlow<Long>(0)

        val start = getTimeMark()
        val df = DriverFactory()
        runTestOne(df) { text, finished ->
            totalTime.update { getDiffMs(start) }
            println("Time spent: ${totalTime.value} ms\n" + text.take(40))
            if (finished) { continuation.resume(Unit) }
        }
    }
}

suspend fun test2() {
    println("\n=== TEST 2 ===\n")

    suspendCoroutine { continuation ->
        val totalTime = MutableStateFlow<Long>(0)
        val start = MutableStateFlow(getTimeMark())
        val df = DriverFactory()
        runTestTwo(df, started = {
            start.update { getTimeMark() }
        }) { text, finished ->
            totalTime.update { getDiffMs(start.value) }
            println("Time spent: ${totalTime.value} ms\n" + text.take(40))
            if (finished) { continuation.resume(Unit) }
        }
    }
}