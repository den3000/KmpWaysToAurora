import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asPromise
import kotlinx.coroutines.async

/**
 * Init fun for run after ready index.html
 */
@OptIn(ExperimentalJsExport::class)
@JsExport
fun init() {
    sendEvent("Init")
}

/**
 * NPM package for generate UUID
 */
@JsModule("uuid")
@JsNonModule
external object Uuid {
    fun v4(): String
}

@OptIn(ExperimentalJsExport::class)
@JsExport
fun platformJS() = platform()

@OptIn(ExperimentalJsExport::class)
@JsExport
fun getKtorIoWelcomePageAsTextJS(): Any {
    val scope = CoroutineScope(getExecutionContext())
    val caller = Uuid.v4()
    scope.async {
        getKtorIoWelcomePageAsText()
    }.asPromise().then {
        sendEventResponse(caller, response = it)
    }
    return caller
}