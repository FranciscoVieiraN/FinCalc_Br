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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.financalcbr.app.R
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
            label = { Text(stringResource(R.string.label_initial_value)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = CurrencyVisualTransformation(),
            singleLine = true
        )


        // 2. Valor Mensal
        OutlinedTextField(
            value = state.monthlyValue,
            onValueChange = { viewModel.onEvent(CompoundInterestEvent.OnMonthlyValueChange(it)) },
            label = { Text(stringResource(R.string.label_monthly_value)) },
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
                label = { Text(stringResource(R.string.label_interest_rate)) },
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
                label = { Text(stringResource(R.string.label_period)) },
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
            Text(stringResource(R.string.button_calculate))
        }
    }

    if (state.showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(CompoundInterestEvent.OnDismissDialog) },
            confirmButton = {
                TextButton(onClick = { viewModel.onEvent(CompoundInterestEvent.OnDismissDialog) }) {
                    Text(stringResource(R.string.button_ok))
                }
            },
            title = {
                Text(
                    text = stringResource(R.string.calculation_result_title),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ResultRow(stringResource(R.string.res_total_invested), currencyFormatter.format(state.totalInvested))
                    ResultRow(stringResource(R.string.res_total_with_interest), currencyFormatter.format(state.totalWithInterest))
                    ResultRow(stringResource(R.string.res_total_interest_only), currencyFormatter.format(state.interestEarned), isHighlight = true)
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