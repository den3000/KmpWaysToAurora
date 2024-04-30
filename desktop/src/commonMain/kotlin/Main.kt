import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update

suspend fun main() {
    std()
    serialization()
    coroutine()
    flow()
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

suspend fun coroutine() {
    println("\n=== COROUTINES ===\n")

    println("suspend started")
    val str = triggerCoroutine(4000)
    println("suspend finished")
    println(println("text: $str"))
}

suspend fun flow() {
    println("\n=== FLOWS ===\n")

    println("flow started")
    triggerFlow(2000)
        .flowOn(getExecutionContext())
        .collect {
            println("flow collected")
            println(println("text: $it"))
        }
}

suspend fun ktor() {
    println("\n=== KTOR ===\n")

    println("suspend started")
    val str = getKtorIoWelcomePageAsText()
    println("suspend finished")
    println(println("text: $str"))
}

suspend fun db() {
    println("\n=== DB ===\n")

    println("flow started")
    getProgrammersFromSqlDelight(DriverFactory())
        .flowOn(getExecutionContext())
        .collect {
            println("flow collected")
            println(println(it))
        }
}

suspend fun test1() {
    println("\n=== TEST 1 ===\n")

    val totalTime = MutableStateFlow<Long>(0)
    val start = getTimeMark()
    val df = DriverFactory()

    runTestOne(df)
        .flowOn(getExecutionContext())
        .collect { text ->
            totalTime.update { getDiffMs(start) }
            println("Time spent: ${totalTime.value} ms\n" + text.take(40))
        }
}

suspend fun test2() {
    println("\n=== TEST 2 ===\n")

    val totalTime = MutableStateFlow<Long>(0)
    val start = MutableStateFlow(getTimeMark())
    val df = DriverFactory()
    runTestTwo(df, started = {
        start.update { getTimeMark() }
    }).collect {text ->
        totalTime.update { getDiffMs(start.value) }
        println("Time spent: ${totalTime.value} ms\n" + text.take(40))
    }
}