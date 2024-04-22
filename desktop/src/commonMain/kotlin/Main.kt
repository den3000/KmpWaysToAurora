import com.den3000.kmpwaystoaurora.Database
import com.den3000.kmpwaystoaurora.hockey.data.ProgrammerQueries

fun main() {
    std()
    serialization()
    coroutines()
    ktor()
    db()
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

    var end = false
    triggerCoroutine(1000) { text, finished ->
        println(text)
        end = finished
    }
    while (!end) { /**/ }
}

fun ktor() {
    println("\n=== KTOR ===\n")

    var end = false
    getKtorIoWelcomePageAsText { text, finished ->
        println(text)
        end = finished
    }
    while (!end) { /**/ }
}

fun db() {
    println("\n=== DB ===\n")

    val df = DriverFactory()
    val dr = df.createDriver() ?: return
    val db = Database(dr)

    val programmerQueries: ProgrammerQueries = db.programmerQueries
    println(programmerQueries.selectAll().executeAsList().joinToString(separator = "\n"))
}