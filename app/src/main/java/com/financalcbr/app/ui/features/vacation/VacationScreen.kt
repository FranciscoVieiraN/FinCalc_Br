package com.financalcbr.app.ui.features.vacation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.financalcbr.app.R
import com.financalcbr.app.ui.common.CurrencyVisualTransformation
import java.text.NumberFormat
import java.util.Locale

@Composable
fun VacationScreen(
    viewModel: VacationViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    VacationContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun VacationContent(
    state: VacationUiState,
    onEvent: (VacationUiEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.title_vacation),
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = state.grossSalary,
            onValueChange = { onEvent(VacationUiEvent.OnGrossSalaryChanged(it)) },
            label = { Text(stringResource(R.string.label_salario_bruto)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = CurrencyVisualTransformation(),
            isError = state.grossSalaryError != null,
            supportingText = state.grossSalaryError?.let { { Text(it) } }
        )

        OutlinedTextField(
            value = state.vacationDays,
            onValueChange = { onEvent(VacationUiEvent.OnVacationDaysChanged(it)) },
            label = { Text(stringResource(R.string.label_vacation_days)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = state.vacationDaysError != null,
            supportingText = state.vacationDaysError?.let { { Text(it) } }
        )

        OutlinedTextField(
            value = state.dependentsCount,
            onValueChange = { onEvent(VacationUiEvent.OnDependentsChanged(it)) },
            label = { Text(stringResource(R.string.label_dependents_count)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = state.sellVacation,
                onClick = { onEvent(VacationUiEvent.OnToggleSellVacation) },
                label = { Text(stringResource(R.string.label_sell_vacation)) },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                selected = state.advanceThirteen,
                onClick = { onEvent(VacationUiEvent.OnToggleAdvanceThirteen) },
                label = { Text(stringResource(R.string.label_advance_thirteen)) },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { onEvent(VacationUiEvent.OnClearClicked) },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.button_clear))
            }
            Button(
                onClick = { onEvent(VacationUiEvent.OnCalculateClicked) },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.button_calculate))
            }
        }

        if (state.showResults) {
            Spacer(modifier = Modifier.height(8.dp))
            VacationResultCard(state, currencyFormatter)
        }
    }
}

@Composable
fun VacationResultCard(state: VacationUiState, formatter: NumberFormat) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = stringResource(R.string.calculation_summary_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            HorizontalDivider()

            EarningsRow(
                stringResource(R.string.label_vacation_days_value),
                formatter.format(state.vacationDaysValue)
            )
            EarningsRow(
                stringResource(R.string.label_one_third),
                formatter.format(state.oneThirdValue)
            )

            if (state.abonoValue > 0) {
                EarningsRow(
                    stringResource(R.string.label_abono),
                    formatter.format(state.abonoValue)
                )
                EarningsRow(
                    stringResource(R.string.label_one_third_abono),
                    formatter.format(state.oneThirdAbonoValue)
                )
            }
            if (state.thirteenAdvanceValue > 0) {
                EarningsRow(
                    stringResource(R.string.label_thirteen_advance),
                    formatter.format(state.thirteenAdvanceValue)
                )
            }

            Text(
                text = "${stringResource(R.string.label_total_earnings)}: ${formatter.format(state.totalEarnings)}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )

            HorizontalDivider()

            DeductionRow(
                stringResource(R.string.label_inss_deduction),
                "- ${formatter.format(state.inssDeduction)}"
            )
            DeductionRow(
                stringResource(R.string.label_irrf_deduction),
                "- ${formatter.format(state.irrfDeduction)}"
            )

            Text(
                text = "${stringResource(R.string.label_total_deductions)}: ${formatter.format(state.totalDeductions)}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.Red
            )

            HorizontalDivider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.label_net_vacation),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = formatter.format(state.netVacation),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )
            }
        }
    }
}

@Composable
fun EarningsRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun DeductionRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Text(text = value, style = MaterialTheme.typography.bodyLarge, color = Color.Red)
    }
}

@Preview(showBackground = true)
@Composable
fun VacationContentPreview() {
    val previewState = VacationUiState(
        grossSalary = "500000",
        vacationDays = "30",
        dependentsCount = "0",
        sellVacation = true,
        advanceThirteen = true,
        vacationDaysValue = 5000.0,
        oneThirdValue = 1666.67,
        abonoValue = 1666.67,
        oneThirdAbonoValue = 555.56,
        thirteenAdvanceValue = 2500.0,
        inssDeduction = 758.49,
        irrfDeduction = 642.38,
        totalEarnings = 11388.90,
        totalDeductions = 1400.87,
        netVacation = 9988.03,
        showResults = true
    )
    VacationContent(state = previewState, onEvent = {})
}
