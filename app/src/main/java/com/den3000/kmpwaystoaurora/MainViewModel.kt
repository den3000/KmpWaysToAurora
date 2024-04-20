package com.den3000.kmpwaystoaurora

import DataClass
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import deserializeFromString
import getDataClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import platform
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
        triggerCoroutine(1000) { text, finished ->
            this@MainViewModel.text.update { text }
        }
    }

    fun ktor() {
        text.update { "ktor" }
    }

    fun db() {
        text.update { "db" }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainViewModel()
            }
        }
    }
}