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