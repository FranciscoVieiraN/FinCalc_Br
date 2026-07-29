package com.financalcbr.app.ui.features.overtime

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@Composable
fun OvertimeScreen(
    viewModel: OvertimeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val currencyFormatter = remember {
        java.text.NumberFormat.getCurrencyInstance(java.util.Locale.forLanguageTag("pt-BR"))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Salário Bruto
        OutlinedTextField(
            value = state.salary,
            onValueChange = { viewModel.onEvent(OvertimeEvent.OnSalaryChange(it)) },
            label = { Text(stringResource(R.string.label_gross_salary)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = CurrencyVisualTransformation(),
            singleLine = true,
            isError = state.isSalaryError,
            supportingText = {
                if (state.isSalaryError) {
                    Text(stringResource(R.string.error_salary_required))
                }
            }
        )

        // 2. Jornada Mensal de Horas
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = state.monthlyHours,
                onValueChange = { viewModel.onEvent(OvertimeEvent.OnMonthlyHoursChange(it)) },
                label = { Text(stringResource(R.string.label_monthly_hours)) },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                TextButton(onClick = { viewModel.onEvent(OvertimeEvent.OnMonthlyHoursChange("220")) }) {
                    Text(stringResource(R.string.label_hours_220))
                }
                TextButton(onClick = { viewModel.onEvent(OvertimeEvent.OnMonthlyHoursChange("180")) }) {
                    Text(stringResource(R.string.label_hours_180))
                }
            }
        }

        // 3. Quantidade de Horas Extras
        OutlinedTextField(
            value = state.extraHoursCount,
            onValueChange = { viewModel.onEvent(OvertimeEvent.OnExtraHoursCountChange(it)) },
            label = { Text(stringResource(R.string.label_extra_hours_count)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        // 4. Adicional da Hora Extra
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = state.bonusPercentage,
                onValueChange = { viewModel.onEvent(OvertimeEvent.OnBonusPercentageChange(it)) },
                label = { Text(stringResource(R.string.label_bonus_percentage)) },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = PercentageVisualTransformation(),
                singleLine = true
            )
            Column (verticalArrangement = Arrangement.spacedBy(4.dp)) {
                TextButton(onClick = { viewModel.onEvent(OvertimeEvent.OnBonusPercentageChange("5000")) }) {
                    Text(stringResource(R.string.label_percentage_50))
                }
                TextButton(onClick = { viewModel.onEvent(OvertimeEvent.OnBonusPercentageChange("10000")) }) {
                    Text(stringResource(R.string.label_percentage_100))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.onEvent(OvertimeEvent.OnCalculateClick) },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.salary.isNotBlank()
        ) {
            Text(stringResource(R.string.button_calculate))
        }
    }

    if (state.showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(OvertimeEvent.OnDismissDialog) },
            confirmButton = {
                TextButton(onClick = { viewModel.onEvent(OvertimeEvent.OnDismissDialog) }) {
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
                    ResultRow(stringResource(R.string.res_common_hour), currencyFormatter.format(state.commonHourValue))
                    ResultRow(stringResource(R.string.res_extra_hour_value), currencyFormatter.format(state.extraHourValue))
                    ResultRow(stringResource(R.string.res_total_gross), currencyFormatter.format(state.totalAmount), isHighlight = true)
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
