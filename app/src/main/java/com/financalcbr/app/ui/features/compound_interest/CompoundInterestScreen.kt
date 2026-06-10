package com.financalcbr.app.ui.features.compound_interest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.financalcbr.app.ui.common.CurrencyVisualTransformation
import com.financalcbr.app.ui.common.PercentageVisualTransformation
import com.financalcbr.app.ui.components.PeriodSelector
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CompoundInterestScreen(
    viewModel: CompoundInterestViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Valor Inicial
        OutlinedTextField(
            value = state.initialValue,
            onValueChange = { viewModel.onEvent(CompoundInterestEvent.OnInitialValueChange(it)) },
            label = { Text("Valor inicial") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = CurrencyVisualTransformation(),
            singleLine = true
        )


        // 2. Valor Mensal
        OutlinedTextField(
            value = state.monthlyValue,
            onValueChange = { viewModel.onEvent(CompoundInterestEvent.OnMonthlyValueChange(it)) },
            label = { Text("Valor mensal") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = CurrencyVisualTransformation(),
            singleLine = true
        )

        // 3. Taxa de Juros
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = state.interestRate,
                onValueChange = { viewModel.onEvent(CompoundInterestEvent.OnInterestRateChange(it)) },
                label = { Text("Taxa de juros") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = PercentageVisualTransformation(),
                singleLine = true
            )
            PeriodSelector(
                selectedPeriod = state.interestRatePeriod,
                onPeriodSelected = {
                    viewModel.onEvent(
                        CompoundInterestEvent.OnInterestRatePeriodChange(
                            it
                        )
                    )
                }
            )
        }

        // 4. Período
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = state.investmentPeriod,
                onValueChange = {
                    viewModel.onEvent(
                        CompoundInterestEvent.OnInvestmentPeriodChange(
                            it
                        )
                    )
                },
                label = { Text("Período") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            PeriodSelector(
                selectedPeriod = state.investmentPeriodType,
                onPeriodSelected = {
                    viewModel.onEvent(
                        CompoundInterestEvent.OnInvestmentPeriodTypeChange(
                            it
                        )
                    )
                }
            )
        }

        Button(
            onClick = { viewModel.onEvent(CompoundInterestEvent.OnCalculateClick) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular")
        }
    }

    if (state.showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(CompoundInterestEvent.OnDismissDialog) },
            confirmButton = {
                TextButton(onClick = { viewModel.onEvent(CompoundInterestEvent.OnDismissDialog) }) {
                    Text("OK")
                }
            },
            title = {
                Text(
                    text = "Resultado do Cálculo",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ResultRow("Valor total investido:", currencyFormatter.format(state.totalInvested))
                    ResultRow("Valor total com juros:", currencyFormatter.format(state.totalWithInterest))
                    ResultRow("Total em juros:", currencyFormatter.format(state.interestEarned), isHighlight = true)
                }
            }
        )
    }
}

@Composable
private fun ResultRow(
    label: String,
    value: String,
    isHighlight: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Normal,
            color = if (isHighlight) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}