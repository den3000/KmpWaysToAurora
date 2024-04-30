package com.den3000.kmpwaystoaurora

import DataClass
import DriverFactory
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import deserializeFromString
import getCallbackContext
import getDataClass
import getDiffMs
import getKtorIoWelcomePageAsText
import getProgrammersFromSqlDelight
import getTimeMark
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import platform
import runTestOne
import runTestTwo
import serializeToString
import triggerCoroutine
import triggerFlow
import triggerLambda

class MainViewModel: ViewModel() {

    var text = MutableStateFlow("")

    fun std() {
        val ktText = platform()
        val ktDataClass1 = getDataClass()
        val ktDataClass1Str = ktDataClass1.toString()

        val ktDataClass2 = DataClass(
            int = 2,
            string = "Some android string"
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
        var result = "Original to string: $dc"
        val serialized = serializeToString(dc)
        result += "\nSerialized: $serialized"
        dc = deserializeFromString(serialized)
        result += "\nDeserialized from string: $dc"

        text.update { result }
    }

    fun coroutines() {
        viewModelScope.launch {
            ui_print("Coroutine started")
            val result = triggerCoroutine(1000)
            ui_print(result)
        }
    }

    fun flow() {
        text.update { "flow started" }

        viewModelScope.launch {
            triggerFlow(1000)
                .collect { result ->
                    ui_print(result)
                }
        }
    }

    fun ktor() {
        text.update { "Request started" }

        viewModelScope.launch {
            val result = getKtorIoWelcomePageAsText()
            ui_print(result)
        }
    }

    fun db(context: Context) {
        text.update { "DB started" }

        val df = DriverFactory(context)
        viewModelScope.launch {
            getProgrammersFromSqlDelight(df)
                .collect { result ->
                    ui_print(text.value + "\n$result")
                }
        }
    }

    fun test1(ctx: Context) {
        text.update { "Test 1 started" }

        val totalTime = MutableStateFlow<Long>(0)
        val start = getTimeMark()
        val df = DriverFactory(ctx)
        viewModelScope.launch {
            runTestOne(df)
                .onCompletion {
                    ui_print("Time spent: ${totalTime.value} ms")
                }.collect { result ->
                    totalTime.update { getDiffMs(start) }
                    ui_print("Time spent: ${totalTime.value} ms\n" + result)
                }
        }
    }

    fun test2(ctx: Context) {
        text.update { "Test 2 started" }

        val totalTime = MutableStateFlow<Long>(0)
        val start = MutableStateFlow(getTimeMark())
        val df = DriverFactory(ctx)
        viewModelScope.launch {
            runTestTwo(df, started = {
                start.update { getTimeMark() }
            }).onCompletion {
                ui_print("Time spent: ${totalTime.value} ms")
            }.collect { result ->
                totalTime.update { getDiffMs(start.value) }
                ui_print("Time spent: ${totalTime.value} ms\n" + result)
            }
        }
    }

    private suspend fun ui_print(text: String) {
        withContext(getCallbackContext()) {
            this@MainViewModel.text.update { text }
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