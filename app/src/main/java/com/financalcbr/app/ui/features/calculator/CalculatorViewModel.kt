package com.financalcbr.app.ui.features.calculator

import androidx.lifecycle.ViewModel
import com.financalcbr.app.domain.calculator.CalculateExpressionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val calculateExpression: CalculateExpressionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CalculatorState())
    val state = _state.asStateFlow()

    fun onEvent(event: CalculatorEvent) {
        when (event) {
            is CalculatorEvent.NumberPressed -> addNumber(event.number)
            is CalculatorEvent.OperatorPressed -> addOperator(event.operator)
            CalculatorEvent.Comma -> addComma()
            CalculatorEvent.Delete -> deleteLast()
            CalculatorEvent.Clear -> clear()
            CalculatorEvent.Calculate -> calculate()
        }
    }

    private fun calculate() {
        _state.update {
            it.copy(
                expression = try {
                    calculateExpression(it.expression)
                } catch (e: Exception) {
                    "Erro"
                }
            )
        }
    }

    private fun addNumber(number: String) {
        _state.update {
            val exp = it.expression
            it.copy(
                expression = if (exp == "0") number else exp + number
            )
        }
    }

    private fun addOperator(operator: Char) {
        _state.update {
            val exp = it.expression
            if (exp.last().isDigit()) {
                it.copy(expression = "$exp$operator")
            } else it
        }
    }

    private fun addComma() {
        _state.update {
            val exp = it.expression
            val lastNumber = exp.takeLastWhile { it.isDigit() || it == ',' }

            if (!lastNumber.contains(",")) {
                it.copy(expression = "$exp,")
            } else it
        }
    }

    private fun deleteLast() {
        _state.update {
            val exp = it.expression
            if (exp.length <= 1) it.copy(expression = "0")
            else it.copy(expression = exp.dropLast(1))
        }
    }

    private fun clear() {
        _state.update { CalculatorState() }
    }

}
