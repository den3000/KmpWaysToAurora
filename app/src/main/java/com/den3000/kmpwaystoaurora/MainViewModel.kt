package com.den3000.kmpwaystoaurora

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import getDataClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import platform

class MainViewModel: ViewModel() {

    var text = MutableStateFlow("")

    fun std() {
        val ktText = platform()
        val ktDataClass = getDataClass()
        val ktDataClassStr = ktDataClass.toString()

        text.update { "Hello, $ktText\n$ktDataClassStr" }
    }

    fun serialization() {
        text.update { "serialization" }
    }

    fun coroutines() {
        text.update { "coroutines" }
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