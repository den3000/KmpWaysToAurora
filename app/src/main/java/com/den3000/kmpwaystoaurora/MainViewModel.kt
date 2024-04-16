package com.den3000.kmpwaystoaurora

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class MainViewModel: ViewModel() {

    fun std() {
        println("std")
    }

    fun serialization() {
        println("serialization")
    }

    fun coroutines() {
        println("coroutines")
    }

    fun ktor() {
        println("ktor")
    }

    fun db() {
        println("db")
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainViewModel()
            }
        }
    }
}