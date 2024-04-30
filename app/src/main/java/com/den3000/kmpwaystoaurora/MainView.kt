package com.den3000.kmpwaystoaurora

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.den3000.kmpwaystoaurora.ui.theme.KmpWaysToAuroraTheme

@Composable
fun MainView(
    text: String,
    modifier: Modifier = Modifier,
    onStd: (() -> Unit)? = null,
    onSerialization: (() -> Unit)? = null,
    onCoroutines: (() -> Unit)? = null,
    onFlow: (() -> Unit)? = null,
    onKtor: (() -> Unit)? = null,
    onDb: (() -> Unit)? = null,
    onTest1: (() -> Unit)? = null,
    onTest2: (() -> Unit)? = null,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (
            refText,
            refBtStd,
            refBtSerialization,
            refBtCoroutines,
            refBtFlow,
            refBtKtor,
            refBtDb,
            refBtTest1,
            refBtTest2,
        ) = createRefs()

        val glCenterX = createGuidelineFromStart(0.5f)

        Text(
            text = text,
            modifier = modifier.constrainAs(refText) {
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(refBtStd.top, 16.dp)
            }.fillMaxHeight()
        )

        Button(
            onClick = onStd ?: {},
            modifier.constrainAs(refBtStd) {
                width = Dimension.fillToConstraints
                height = Dimension.value(40.dp)
                start.linkTo(parent.start)
                end.linkTo(glCenterX, 8.dp)
                bottom.linkTo(refBtCoroutines.top, 16.dp)
            }) {
            Text(text = "Std")
        }

        Button(
            onClick = onSerialization ?: {},
            modifier.constrainAs(refBtSerialization) {
                width = Dimension.fillToConstraints
                height = Dimension.value(40.dp)
                start.linkTo(glCenterX, 8.dp)
                end.linkTo(parent.end)
                bottom.linkTo(refBtCoroutines.top, 16.dp)
            }) {
            Text(text = "Serialization")
        }

        Button(
            onClick = onCoroutines ?: {},
            modifier.constrainAs(refBtCoroutines) {
                width = Dimension.fillToConstraints
                height = Dimension.value(40.dp)
                start.linkTo(parent.start)
                end.linkTo(glCenterX, 8.dp)
                bottom.linkTo(refBtKtor.top, 16.dp)
            }) {
            Text(text = "Coroutines")
        }

        Button(
            onClick = onFlow ?: {},
            modifier.constrainAs(refBtFlow) {
                width = Dimension.fillToConstraints
                height = Dimension.value(40.dp)
                start.linkTo(glCenterX, 8.dp)
                end.linkTo(parent.end)
                bottom.linkTo(refBtKtor.top, 16.dp)
            }) {
            Text(text = "Flow")
        }

        Button(
            onClick = onKtor ?: {},
            modifier.constrainAs(refBtKtor) {
                width = Dimension.fillToConstraints
                height = Dimension.value(40.dp)
                start.linkTo(parent.start)
                end.linkTo(glCenterX, 8.dp)
                bottom.linkTo(refBtTest1.top, 16.dp)
            }) {
            Text(text = "Ktor")
        }

        Button(
            onClick = onDb ?: {},
            modifier.constrainAs(refBtDb) {
                width = Dimension.fillToConstraints
                height = Dimension.value(40.dp)
                start.linkTo(glCenterX, 8.dp)
                end.linkTo(parent.end)
                bottom.linkTo(refBtTest1.top, 16.dp)
            }) {
            Text(text = "DB")
        }

        Button(
            onClick = onTest1 ?: {},
            modifier.constrainAs(refBtTest1) {
                width = Dimension.fillToConstraints
                height = Dimension.value(40.dp)
                start.linkTo(parent.start)
                end.linkTo(glCenterX, 8.dp)
                bottom.linkTo(parent.bottom)
            }) {
            Text(text = "TEST 1")
        }

        Button(
            onClick = onTest2 ?: {},
            modifier.constrainAs(refBtTest2) {
                width = Dimension.fillToConstraints
                height = Dimension.value(40.dp)
                start.linkTo(glCenterX, 8.dp)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }) {
            Text(text = "TEST 2")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainViewPreview() {
    KmpWaysToAuroraTheme {
        MainView("Hello, Android!")
    }
}