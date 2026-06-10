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
                            .height(buttonHeight.dp)
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
                    .height(buttonHeight.dp)
            ) {
                onEvent(CalculatorEvent.NumberPressed("0"))
            }

            CalculatorButton(
                label = ",",
                modifier = Modifier
                    .weight(1f)
                    .height(buttonHeight.dp)
            ) {
                onEvent(CalculatorEvent.Comma)
            }

            CalculatorButton(
                label = ConstantsUtils.EQUAL.toString(),
                modifier = Modifier
                    .weight(1f)
                    .height(buttonHeight.dp)
            ) {
                onEvent(CalculatorEvent.Calculate)
            }
        }
    }
}
