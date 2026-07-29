package com.financalcbr.app.ui.features.net_salary

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.financalcbr.app.R
import com.financalcbr.app.ui.common.CurrencyVisualTransformation
import java.text.NumberFormat
import java.util.Locale

@Composable
fun NetSalaryScreen(
    viewModel: NetSalaryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    NetSalaryContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun NetSalaryContent(
    state: NetSalaryUiState,
    onEvent: (NetSalaryUiEvent) -> Unit
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
            text = stringResource(R.string.title_net_salary_clt),
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = state.grossSalary,
            onValueChange = { onEvent(NetSalaryUiEvent.OnGrossSalaryChanged(it)) },
            label = { Text(stringResource(R.string.label_salario_bruto)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = CurrencyVisualTransformation(),
            isError = state.grossSalaryError != null,
            supportingText = state.grossSalaryError?.let { { Text(it) } }
        )

        OutlinedTextField(
            value = state.dependentsCount,
            onValueChange = { onEvent(NetSalaryUiEvent.OnDependentsChanged(it)) },
            label = { Text(stringResource(R.string.label_dependents_count)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = state.dependentsError != null,
            supportingText = state.dependentsError?.let { { Text(it) } }
        )

        OutlinedTextField(
            value = state.otherDeductions,
            onValueChange = { onEvent(NetSalaryUiEvent.OnOtherDeductionsChanged(it)) },
            label = { Text(stringResource(R.string.label_other_deductions)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = CurrencyVisualTransformation(),
            isError = state.otherDeductionsError != null,
            supportingText = state.otherDeductionsError?.let { { Text(it) } }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { onEvent(NetSalaryUiEvent.OnClearClicked) },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.button_clear))
            }
            Button(
                onClick = { onEvent(NetSalaryUiEvent.OnCalculateClicked) },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.button_calculate))
            }
        }

        if (state.showResults) {
            Spacer(modifier = Modifier.height(8.dp))
            ResultCard(state, currencyFormatter)
        }
    }
}

@Composable
fun ResultCard(state: NetSalaryUiState, formatter: NumberFormat) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.calculation_summary_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            HorizontalDivider()

            ResultRow(stringResource(R.string.label_salario_bruto), formatter.format(state.grossSalaryVal), Color.Unspecified)
            ResultRow(stringResource(R.string.label_inss_deduction), "- ${formatter.format(state.inssDeduction)}", Color.Red)
            ResultRow(stringResource(R.string.label_irrf_deduction), "- ${formatter.format(state.irrfDeduction)}", Color.Red)
            if (state.otherDeductionsVal > 0) {
                ResultRow(stringResource(R.string.label_other_deductions_short), "- ${formatter.format(state.otherDeductionsVal)}", Color.Red)
            }

            HorizontalDivider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.label_net_salary),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = formatter.format(state.netSalary),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32) // Verde escuro
                )
            }
        }
    }
}

@Composable
fun ResultRow(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Text(text = value, style = MaterialTheme.typography.bodyLarge, color = color)
    }
}
