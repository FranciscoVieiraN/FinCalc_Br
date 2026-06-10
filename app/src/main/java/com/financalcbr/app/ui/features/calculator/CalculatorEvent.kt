package com.financalcbr.app.ui.features.calculator

sealed class CalculatorEvent {
    data class NumberPressed(val number: String) : CalculatorEvent()
    data class OperatorPressed(val operator: Char) : CalculatorEvent()
    data object Clear : CalculatorEvent()
    data object Calculate : CalculatorEvent()
    data object Delete : CalculatorEvent()
    data object Comma : CalculatorEvent()
}