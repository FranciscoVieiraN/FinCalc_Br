package com.financalcbr.app.ui.features.vacation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.financalcbr.app.R
import com.financalcbr.app.ui.common.CurrencyVisualTransformation
import com.financalcbr.app.ui.common.TestTags
import com.financalcbr.app.ui.theme.Dimens
import com.financalcbr.app.ui.theme.FinanCalcBRTheme
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
            .padding(Dimens.Space16)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(Dimens.Space16)
    ) {
        Text(
            text = stringResource(R.string.title_vacation),
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = state.grossSalary,
            onValueChange = { onEvent(VacationUiEvent.OnGrossSalaryChanged(it)) },
            label = { Text(stringResource(R.string.label_salario_bruto)) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.VACATION.INPUT_GROSS_SALARY),
            shape = RoundedCornerShape(Dimens.CornerMedium),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = CurrencyVisualTransformation(),
            isError = state.grossSalaryError != null,
            supportingText = state.grossSalaryError?.let { { Text(it) } }
        )

        OutlinedTextField(
            value = state.vacationDays,
            onValueChange = { onEvent(VacationUiEvent.OnVacationDaysChanged(it)) },
            label = { Text(stringResource(R.string.label_vacation_days)) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.VACATION.INPUT_VACATION_DAYS),
            shape = RoundedCornerShape(Dimens.CornerMedium),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = state.vacationDaysError != null,
            supportingText = state.vacationDaysError?.let { { Text(it) } }
        )

        OutlinedTextField(
            value = state.dependentsCount,
            onValueChange = { onEvent(VacationUiEvent.OnDependentsChanged(it)) },
            label = { Text(stringResource(R.string.label_dependents_count)) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.VACATION.INPUT_DEPENDENTS),
            shape = RoundedCornerShape(Dimens.CornerMedium),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Space8)
        ) {
            FilterChip(
                selected = state.sellVacation,
                onClick = { onEvent(VacationUiEvent.OnToggleSellVacation) },
                label = { Text(stringResource(R.string.label_sell_vacation)) },
                modifier = Modifier
                    .weight(1f)
                    .testTag(TestTags.VACATION.SWITCH_SELL_VACATION)
            )
            FilterChip(
                selected = state.advanceThirteen,
                onClick = { onEvent(VacationUiEvent.OnToggleAdvanceThirteen) },
                label = { Text(stringResource(R.string.label_advance_thirteen)) },
                modifier = Modifier
                    .weight(1f)
                    .testTag(TestTags.VACATION.SWITCH_ADVANCE_THIRTEEN)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Space8)
        ) {
            OutlinedButton(
                onClick = { onEvent(VacationUiEvent.OnClearClicked) },
                modifier = Modifier
                    .weight(1f)
                    .height(Dimens.ButtonHeight)
                    .testTag(TestTags.VACATION.BTN_CLEAR),
                shape = RoundedCornerShape(Dimens.CornerMedium)
            ) {
                Text(stringResource(R.string.button_clear))
            }
            Button(
                onClick = { onEvent(VacationUiEvent.OnCalculateClicked) },
                modifier = Modifier
                    .weight(1f)
                    .height(Dimens.ButtonHeight)
                    .testTag(TestTags.VACATION.BTN_CALCULATE),
                shape = RoundedCornerShape(Dimens.CornerMedium)
            ) {
                Text(stringResource(R.string.button_calculate))
            }
        }

        if (state.showResults) {
            Spacer(modifier = Modifier.height(Dimens.Space8))
            VacationResultCard(state, currencyFormatter)
        }
    }
}

@Composable
fun VacationResultCard(state: VacationUiState, formatter: NumberFormat) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(TestTags.VACATION.CARD_RESULT),
        shape = RoundedCornerShape(Dimens.CornerLarge),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.CardElevation
        )
    ) {
        Column(
            modifier = Modifier.padding(Dimens.Space16),
            verticalArrangement = Arrangement.spacedBy(Dimens.Space8)
        ) {
            Text(
                text = stringResource(R.string.calculation_summary_title),
                style = MaterialTheme.typography.titleMedium
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            SectionHeader(stringResource(R.string.label_proventos))
            EarningsRow(
                stringResource(R.string.label_vacation_days_value),
                formatter.format(state.vacationDaysValue),
                valueTestTag = TestTags.VACATION.TXT_VACATION_DAYS_VALUE
            )
            EarningsRow(
                stringResource(R.string.label_one_third),
                formatter.format(state.oneThirdValue),
                valueTestTag = TestTags.VACATION.TXT_ONE_THIRD
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

            SummaryRow(
                label = stringResource(R.string.label_total_earnings),
                value = formatter.format(state.totalEarnings),
                testTag = TestTags.VACATION.TXT_TOTAL_EARNINGS,
                color = MaterialTheme.colorScheme.onSurface
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            SectionHeader(stringResource(R.string.label_descontos))
            DeductionRow(
                stringResource(R.string.label_inss_deduction),
                "- ${formatter.format(state.inssDeduction)}",
                valueTestTag = TestTags.VACATION.TXT_INSS_DEDUCTION
            )
            DeductionRow(
                stringResource(R.string.label_irrf_deduction),
                "- ${formatter.format(state.irrfDeduction)}",
                valueTestTag = TestTags.VACATION.TXT_IRRF_DEDUCTION
            )

            SummaryRow(
                label = stringResource(R.string.label_total_deductions),
                value = formatter.format(state.totalDeductions),
                testTag = TestTags.VACATION.TXT_TOTAL_DEDUCTIONS,
                color = MaterialTheme.colorScheme.error
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            NetSummaryRow(
                label = stringResource(R.string.label_net_vacation),
                value = formatter.format(state.netVacation),
                testTag = TestTags.VACATION.TXT_NET_VACATION
            )
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(top = Dimens.Space4)
    )
}

@Composable
private fun NetSummaryRow(label: String, value: String, testTag: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.CornerMedium))
            .padding(Dimens.Space12)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .testTag(testTag)
                    .semantics { contentDescription = testTag }
            )
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String, testTag: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = color,
            modifier = Modifier
                .testTag(testTag)
                .semantics { contentDescription = testTag }
        )
    }
}

@Composable
fun EarningsRow(label: String, value: String, valueTestTag: String? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = if (valueTestTag != null) Modifier
                .testTag(valueTestTag)
                .semantics { contentDescription = valueTestTag }
            else Modifier
        )
    }
}

@Composable
fun DeductionRow(label: String, value: String, valueTestTag: String? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.error,
            modifier = if (valueTestTag != null) Modifier
                .testTag(valueTestTag)
                .semantics { contentDescription = valueTestTag }
            else Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VacationContentPreview() {
    FinanCalcBRTheme {
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
}