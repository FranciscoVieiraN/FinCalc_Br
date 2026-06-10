package com.financalcbr.app.ui.features.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.financalcbr.app.ui.components.CalculatorDisplay
import com.financalcbr.app.ui.components.CalculatorKeyboard
import com.financalcbr.app.utils.getScreenHeight
import com.financalcbr.app.utils.getScreenWidth


@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    CalculatorContent(state, viewModel)
}

@Composable
fun CalculatorContent(
    state: CalculatorState,
    viewModel: CalculatorViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = (0.05 * getScreenWidth()).dp,
                vertical = (0.10 * getScreenHeight()).dp
            ),
        verticalArrangement = Arrangement.SpaceAround
    ) {

        CalculatorDisplay(
            expression = state.expression
        )

        CalculatorKeyboard(
            buttonHeight = (0.085 * getScreenHeight()),
            onEvent = viewModel::onEvent
        )
    }
}

@Preview
@Composable
fun CalculatorScreenPreview() {
    CalculatorScreen()
}