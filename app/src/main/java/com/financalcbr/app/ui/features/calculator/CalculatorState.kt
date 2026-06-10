package com.financalcbr.app.ui.features.calculator

data class CalculatorState(
    val expression: String = "0",
    val result: String = "",
    val buttonHeight: Float = 80.00F
)