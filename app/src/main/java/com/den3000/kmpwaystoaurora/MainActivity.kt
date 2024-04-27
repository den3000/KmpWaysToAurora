package com.den3000.kmpwaystoaurora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.den3000.kmpwaystoaurora.ui.theme.KmpWaysToAuroraTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KmpWaysToAuroraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val ctx = LocalContext.current
                    MainView(
                        text = viewModel.text.collectAsStateWithLifecycle().value,
                        onStd = viewModel::std,
                        onSerialization = viewModel::serialization,
                        onCoroutines = viewModel::coroutines,
                        onKtor = viewModel::ktor,
                        onDb = { viewModel.db(ctx) },
                        onTest1 = { viewModel.test1(ctx) },
                        onTest2 = { viewModel.test2(ctx) },
                    )
                }
            }
        }
    }
}