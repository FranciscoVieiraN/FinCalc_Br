package com.financalcbr.app.ui.features.net_salary

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
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.financalcbr.app.R
import com.financalcbr.app.ui.common.CurrencyVisualTransformation
import com.financalcbr.app.ui.common.TestTags
import com.financalcbr.app.ui.theme.Dimens
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
            .padding(Dimens.Space16)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(Dimens.Space16)
    ) {
        Text(
            text = stringResource(R.string.title_net_salary_clt),
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = state.grossSalary,
            onValueChange = { onEvent(NetSalaryUiEvent.OnGrossSalaryChanged(it)) },
            label = { Text(stringResource(R.string.label_salario_bruto)) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.NET_SALARY.INPUT_GROSS_SALARY),
            shape = RoundedCornerShape(Dimens.CornerMedium),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = CurrencyVisualTransformation(),
            isError = state.grossSalaryError != null,
            supportingText = state.grossSalaryError?.let { { Text(it) } }
        )

        OutlinedTextField(
            value = state.dependentsCount,
            onValueChange = { onEvent(NetSalaryUiEvent.OnDependentsChanged(it)) },
            label = { Text(stringResource(R.string.label_dependents_count)) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.NET_SALARY.INPUT_DEPENDENTS),
            shape = RoundedCornerShape(Dimens.CornerMedium),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = state.dependentsError != null,
            supportingText = state.dependentsError?.let { { Text(it) } }
        )

        OutlinedTextField(
            value = state.otherDeductions,
            onValueChange = { onEvent(NetSalaryUiEvent.OnOtherDeductionsChanged(it)) },
            label = { Text(stringResource(R.string.label_other_deductions)) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.NET_SALARY.INPUT_OTHER_DEDUCTIONS),
            shape = RoundedCornerShape(Dimens.CornerMedium),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = CurrencyVisualTransformation(),
            isError = state.otherDeductionsError != null,
            supportingText = state.otherDeductionsError?.let { { Text(it) } }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Space8)
        ) {
            OutlinedButton(
                onClick = { onEvent(NetSalaryUiEvent.OnClearClicked) },
                modifier = Modifier
                    .weight(1f)
                    .height(Dimens.ButtonHeight)
                    .testTag(TestTags.NET_SALARY.BTN_CLEAR),
                shape = RoundedCornerShape(Dimens.CornerMedium)
            ) {
                Text(stringResource(R.string.button_clear))
            }
            Button(
                onClick = { onEvent(NetSalaryUiEvent.OnCalculateClicked) },
                modifier = Modifier
                    .weight(1f)
                    .height(Dimens.ButtonHeight)
                    .testTag(TestTags.NET_SALARY.BTN_CALCULATE),
                shape = RoundedCornerShape(Dimens.CornerMedium),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(stringResource(R.string.button_calculate))
            }
        }

        if (state.showResults) {
            Spacer(modifier = Modifier.height(Dimens.Space8))
            ResultCard(state, currencyFormatter)
        }
    }
}

@Composable
fun ResultCard(state: NetSalaryUiState, formatter: NumberFormat) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(TestTags.NET_SALARY.CARD_RESULT),
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
            ResultRow(
                stringResource(R.string.label_salario_bruto),
                formatter.format(state.grossSalaryVal),
                color = MaterialTheme.colorScheme.onSurface,
                testTag = TestTags.NET_SALARY.TXT_GROSS_SALARY_VAL
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            SectionHeader(stringResource(R.string.label_descontos))
            ResultRow(
                stringResource(R.string.label_inss_deduction),
                "- ${formatter.format(state.inssDeduction)}",
                color = MaterialTheme.colorScheme.error,
                testTag = TestTags.NET_SALARY.TXT_INSS_DEDUCTION
            )
            ResultRow(
                stringResource(R.string.label_irrf_deduction),
                "- ${formatter.format(state.irrfDeduction)}",
                color = MaterialTheme.colorScheme.error,
                testTag = TestTags.NET_SALARY.TXT_IRRF_DEDUCTION
            )
            if (state.otherDeductionsVal > 0) {
                ResultRow(
                    stringResource(R.string.label_other_deductions_short),
                    "- ${formatter.format(state.otherDeductionsVal)}",
                    color = MaterialTheme.colorScheme.error,
                    testTag = TestTags.NET_SALARY.TXT_OTHER_DEDUCTIONS
                )
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            NetSummaryRow(
                label = stringResource(R.string.label_net_salary),
                value = formatter.format(state.netSalary),
                testTag = TestTags.NET_SALARY.TXT_NET_SALARY
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
            horizontalArrangement = Arrangement.SpaceBetween
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
fun ResultRow(label: String, value: String, color: Color, testTag: String? = null) {
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
            modifier = if (testTag != null) Modifier
                .testTag(testTag)
                .semantics { contentDescription = testTag }
            else Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NetSalaryContentPreview() {
    val previewState = NetSalaryUiState(
        grossSalary = "500000",
        dependentsCount = "0",
        otherDeductions = "0",
        grossSalaryVal = 5000.0,
        inssDeduction = 501.51,
        irrfDeduction = 0.0,
        otherDeductionsVal = 0.0,
        netSalary = 4498.49,
        showResults = true
    )
    NetSalaryContent(state = previewState, onEvent = {})
}