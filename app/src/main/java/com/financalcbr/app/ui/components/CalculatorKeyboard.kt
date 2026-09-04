package com.financalcbr.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.financalcbr.app.ui.common.TestTags
import com.financalcbr.app.ui.features.calculator.CalculatorEvent
import com.financalcbr.app.utils.ConstantsUtils

@Composable
fun CalculatorKeyboard(
    buttonHeight: Double,
    onEvent: (CalculatorEvent) -> Unit
) {

    val buttons = listOf(
        listOf("C", "⌫", ConstantsUtils.PORCENT, ConstantsUtils.DIVD),
        listOf("7", "8", "9", ConstantsUtils.MULT),
        listOf("4", "5", "6", ConstantsUtils.SUB),
        listOf("1", "2", "3", ConstantsUtils.ADD)
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        buttons.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                row.forEach { item ->
                    CalculatorButton(
                        label = item.toString(),
                        modifier = Modifier
                            .weight(1f)
                            .height(buttonHeight.dp),
                        testTag = calculatorTagFor(item.toString())
                    ) {
                        when (item) {
                            "C" -> onEvent(CalculatorEvent.Clear)
                            "⌫" -> onEvent(CalculatorEvent.Delete)

                            ConstantsUtils.ADD,
                            ConstantsUtils.SUB,
                            ConstantsUtils.MULT,
                            ConstantsUtils.DIVD,
                            ConstantsUtils.PORCENT ->
                                onEvent(
                                    CalculatorEvent.OperatorPressed(item as Char)
                                )

                            else -> onEvent(
                                CalculatorEvent.NumberPressed(item.toString())
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            CalculatorButton(
                label = "0",
                modifier = Modifier
                    .weight(2f)
                    .height(buttonHeight.dp),
                testTag = TestTags.CALCULATOR.BTN_0
            ) {
                onEvent(CalculatorEvent.NumberPressed("0"))
            }

            CalculatorButton(
                label = ",",
                modifier = Modifier
                    .weight(1f)
                    .height(buttonHeight.dp),
                testTag = TestTags.CALCULATOR.BTN_COMMA
            ) {
                onEvent(CalculatorEvent.Comma)
            }

            CalculatorButton(
                label = ConstantsUtils.EQUAL.toString(),
                modifier = Modifier
                    .weight(1f)
                    .height(buttonHeight.dp),
                testTag = TestTags.CALCULATOR.BTN_EQUAL
            ) {
                onEvent(CalculatorEvent.Calculate)
            }
        }
    }
}

private fun calculatorTagFor(label: String): String? {
    return when (label) {
        "C" -> TestTags.CALCULATOR.BTN_CLEAR
        "⌫" -> TestTags.CALCULATOR.BTN_DELETE
        ConstantsUtils.PORCENT.toString() -> TestTags.CALCULATOR.BTN_PERCENT
        ConstantsUtils.DIVD.toString() -> TestTags.CALCULATOR.BTN_DIV
        "7" -> TestTags.CALCULATOR.BTN_7
        "8" -> TestTags.CALCULATOR.BTN_8
        "9" -> TestTags.CALCULATOR.BTN_9
        ConstantsUtils.MULT.toString() -> TestTags.CALCULATOR.BTN_MULT
        "4" -> TestTags.CALCULATOR.BTN_4
        "5" -> TestTags.CALCULATOR.BTN_5
        "6" -> TestTags.CALCULATOR.BTN_6
        ConstantsUtils.SUB.toString() -> TestTags.CALCULATOR.BTN_SUB
        "1" -> TestTags.CALCULATOR.BTN_1
        "2" -> TestTags.CALCULATOR.BTN_2
        "3" -> TestTags.CALCULATOR.BTN_3
        ConstantsUtils.ADD.toString() -> TestTags.CALCULATOR.BTN_ADD
        else -> null
    }
}