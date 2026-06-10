package com.financalcbr.app.domain.calculator

import com.financalcbr.app.utils.ConstantsUtils
import java.util.Stack
import javax.inject.Inject


class CalculateExpressionUseCase @Inject constructor() {

    operator fun invoke(expression: String): String {
        if (expression.isBlank()) return "0"

        var exp = expression.replace(",", ".")

        while (exp.isNotEmpty() && ConstantsUtils.INVALID_FINAL_OPERATOR.contains(exp.last())) {
            exp = exp.dropLast(1)
        }

        if (exp.isEmpty()) return "0"

        val processed = processPercentage(exp)
        val result = eval(processed)

        return formatResult(result)
    }

    private fun processPercentage(expression: String): String {
        val regex = Regex("(\\d+(?:\\.\\d+)?)([+\\-×÷])(\\d+(?:\\.\\d+)?)%")

        return regex.replace(expression) { match ->
            val base = match.groupValues[1].toDouble()
            val operator = match.groupValues[2]
            val percent = match.groupValues[3].toDouble()

            val value = base * percent / 100

            when (operator) {
                "+" -> "${base + value}"
                "-" -> "${base - value}"
                "×" -> "${base * (percent / 100)}"
                "÷" -> "${base / (percent / 100)}"
                else -> match.value
            }
        }
    }

    private fun eval(expression: String): Double {
        val ops = Stack<Char>()
        val values = Stack<Double>()

        fun applyOp(op: Char, b: Double, a: Double): Double =
            when (op) {
                ConstantsUtils.ADD -> a + b
                ConstantsUtils.SUB -> a - b
                ConstantsUtils.MULT -> a * b
                ConstantsUtils.DIVD -> a / b
                else -> 0.0
            }

        fun precedence(op: Char) =
            if (op == '+' || op == '-') 1 else 2

        val tokens = expression.toCharArray()
        var i = 0

        while (i < tokens.size) {
            when {
                tokens[i].isDigit() || tokens[i] == '.' -> {
                    val sb = StringBuilder()
                    while (i < tokens.size && (tokens[i].isDigit() || tokens[i] == '.')) {
                        sb.append(tokens[i++])
                    }
                    values.push(sb.toString().toDouble())
                    i--
                }

                tokens[i] == '(' -> ops.push(tokens[i])

                tokens[i] == ')' -> {
                    while (ops.peek() != '(') {
                        values.push(applyOp(ops.pop(), values.pop(), values.pop()))
                    }
                    ops.pop() // remove '('
                }

                tokens[i] in listOf('+', '-', '×', '÷') -> {
                    while (
                        ops.isNotEmpty() && ops.peek() != '(' &&
                        precedence(tokens[i]) <= precedence(ops.peek())
                    ) {
                        values.push(applyOp(ops.pop(), values.pop(), values.pop()))
                    }
                    ops.push(tokens[i])
                }
            }
            i++
        }


        while (ops.isNotEmpty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()))
        }

        return values.pop()
    }

    private fun formatResult(value: Double): String {
        val text = if (value % 1 == 0.0) {
            value.toLong().toString()
        } else {
            "%.2f".format(value)
        }

        return text.replace(".", ",")
    }
}
