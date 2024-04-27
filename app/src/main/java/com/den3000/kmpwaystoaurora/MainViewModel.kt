package com.den3000.kmpwaystoaurora

import DataClass
import DriverFactory
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import deserializeFromString
import getDataClass
import getDiffMs
import getKtorIoWelcomePageAsText
import getProgrammersFromSqlDelight
import getTimeMark
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import platform
import runTestOne
import runTestTwo
import serializeToString
import triggerCoroutine
import triggerLambda

class MainViewModel: ViewModel() {

    var text = MutableStateFlow("")

    fun std() {
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
            fromLambda = "Triggered from lambda on Android"
        }

        text.update {
            "Hello, $ktText\n$ktDataClass1Str\n" +
                "DataClass2\n" +
                "int: $ktDataClass2Int\n" +
                "string: $ktDataClass2Str\n" +
                "fromLambda: $fromLambda"
        }
    }

    fun serialization() {
        var dc = getDataClass()
        var log = "Original to string: $dc"
        val str = serializeToString(dc)
        log += "\nSerialized: $str"
        dc = deserializeFromString(str)
        log += "\nDeserialized from string: $dc"

        text.update { log }
    }

    fun coroutines() {
        triggerCoroutine(1000) { text, _ ->
            this@MainViewModel.text.update { text }
        }
    }

    fun ktor() {
        getKtorIoWelcomePageAsText { text, _ ->
            this@MainViewModel.text.update { text }
        }
    }

    fun db(context: Context) {
        val df = DriverFactory(context)
        getProgrammersFromSqlDelight(df) { text ->
            this@MainViewModel.text.update { text }
        }
    }

    fun test1(ctx: Context) {
        val totalTime = MutableStateFlow<Long>(0)

        val start = getTimeMark()
        val df = DriverFactory(ctx)
        runTestOne(df) { text, _ ->
            totalTime.update { getDiffMs(start) }
            this@MainViewModel.text.update { "Time spent: ${totalTime.value} ms\n" + text }
        }
    }

    fun test2(ctx: Context) {
        val totalTime = MutableStateFlow<Long>(0)

        val start = MutableStateFlow(getTimeMark())
        val df = DriverFactory(ctx)
        runTestTwo(df, started = {
            start.update { getTimeMark() }
        }) { text, _ ->
            totalTime.update { getDiffMs(start.value) }
            this@MainViewModel.text.update { "Time spent: ${totalTime.value} ms\n" + text }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainViewModel()
            }
        }
    }
}