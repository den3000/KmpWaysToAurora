package com.den3000.kmpwaystoaurora.desktop

import com.den3000.kmpwaystoaurora.shared.DriverFactory
import com.den3000.kmpwaystoaurora.shared.getDiffMs
import com.den3000.kmpwaystoaurora.shared.getExecutionContext
import com.den3000.kmpwaystoaurora.shared.getTimeMark
import com.den3000.kmpwaystoaurora.shared.runTestTwo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface TestTwoJtnCallback {
    fun invoke(str: String)
}

fun test2_jtn(callback: TestTwoJtnCallback) {
    CoroutineScope(getExecutionContext()).launch {
        val totalTime = MutableStateFlow<Long>(0)
        val start = MutableStateFlow(getTimeMark())
        val df = DriverFactory()
        runTestTwo(df, started = {
            start.update { getTimeMark() }
        }).collect { text ->
            totalTime.update { getDiffMs(start.value) }
//            println("Time spent: ${totalTime.value} ms\n" + text.take(40))
            callback.invoke("Time spent: ${totalTime.value} ms\n" + text.take(40))
        }
    }
}