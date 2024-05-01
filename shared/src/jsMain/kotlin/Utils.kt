import kotlinx.browser.document
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.asPromise
import kotlinx.coroutines.async
import org.w3c.dom.CustomEvent
import org.w3c.dom.CustomEventInit
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun sendEvent(
    caller: String,
) = _sendEvent(
    caller = caller,
    response = null,
    error = null
)

fun sendEventError(
    caller: String,
    error: String,
) = _sendEvent(
    caller = caller,
    response = null,
    error = error
)

fun <T> sendEventResponse(
    caller: String,
    response: T,
) = _sendEvent(
    caller = caller,
    response = response,
    error = null
)

@Suppress("UNUSED_PARAMETER")
private fun <T> _sendEvent(
    caller: String,
    response: T?,
    error: String?,
) {
    document.dispatchEvent(
        CustomEvent(
            type = "framescript:log",
            eventInitDict = CustomEventInit(
                detail = js("{'caller': caller, 'response': response, 'error': error}")
            ),
        )
    )
}

fun <T : Any> CoroutineScope.promiseWithEvent(
    enable: Boolean,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Any {
    return if (enable) {
        val caller = Uuid.v4()
        async(context, start, block).asPromise().then({
            sendEventResponse(caller = caller, response = it)
        }, {
            sendEventError(caller = caller, error = it.message ?: "Error query")
        })
        caller
    } else {
        async(context, start, block).asPromise()
    }
}