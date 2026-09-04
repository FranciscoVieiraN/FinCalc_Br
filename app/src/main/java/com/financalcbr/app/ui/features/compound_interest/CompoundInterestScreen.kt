package com.financalcbr.app.ui.features.compound_interest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.financalcbr.app.R
import com.financalcbr.app.ui.common.CurrencyVisualTransformation
import com.financalcbr.app.ui.common.PercentageVisualTransformation
import com.financalcbr.app.ui.common.TestTags
import com.financalcbr.app.ui.components.PeriodSelector
import com.financalcbr.app.ui.theme.Dimens
import com.financalcbr.app.ui.theme.FinanCalcBRTheme
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CompoundInterestScreen(
    viewModel: CompoundInterestViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    CompoundInterestContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CompoundInterestContent(
    state: CompoundInterestState,
    onEvent: (CompoundInterestEvent) -> Unit
) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.Space16)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(Dimens.Space16)
    ) {
        Text(
            text = stringResource(R.string.title_compound_interest),
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = state.initialValue,
            onValueChange = { onEvent(CompoundInterestEvent.OnInitialValueChange(it)) },
            label = { Text(stringResource(R.string.label_initial_value)) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.COMPOUND_INTEREST.INPUT_INITIAL_VALUE),
            shape = RoundedCornerShape(Dimens.CornerMedium),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = CurrencyVisualTransformation(),
            singleLine = true
        )

        OutlinedTextField(
            value = state.monthlyValue,
            onValueChange = { onEvent(CompoundInterestEvent.OnMonthlyValueChange(it)) },
            label = { Text(stringResource(R.string.label_monthly_value)) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.COMPOUND_INTEREST.INPUT_MONTHLY_VALUE),
            shape = RoundedCornerShape(Dimens.CornerMedium),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = CurrencyVisualTransformation(),
            singleLine = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.Space8)
        ) {
            OutlinedTextField(
                value = state.interestRate,
                onValueChange = { onEvent(CompoundInterestEvent.OnInterestRateChange(it)) },
                label = { Text(stringResource(R.string.label_interest_rate)) },
                modifier = Modifier
                    .weight(1f)
                    .testTag(TestTags.COMPOUND_INTEREST.INPUT_INTEREST_RATE),
                shape = RoundedCornerShape(Dimens.CornerMedium),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = PercentageVisualTransformation(),
                singleLine = true
            )
            PeriodSelector(
                selectedPeriod = state.interestRatePeriod,
                onPeriodSelected = {
                    onEvent(
                        CompoundInterestEvent.OnInterestRatePeriodChange(
                            it
                        )
                    )
                },
                testTagAnnual = TestTags.COMPOUND_INTEREST.SELECTOR_RATE_PERIOD,
                testTagMonthly = TestTags.COMPOUND_INTEREST.SELECTOR_RATE_PERIOD
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.Space8)
        ) {
            OutlinedTextField(
                value = state.investmentPeriod,
                onValueChange = {
                    onEvent(
                        CompoundInterestEvent.OnInvestmentPeriodChange(
                            it
                        )
                    )
                },
                label = { Text(stringResource(R.string.label_period)) },
                modifier = Modifier
                    .weight(1f)
                    .testTag(TestTags.COMPOUND_INTEREST.INPUT_PERIOD),
                shape = RoundedCornerShape(Dimens.CornerMedium),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            PeriodSelector(
                selectedPeriod = state.investmentPeriodType,
                onPeriodSelected = {
                    onEvent(
                        CompoundInterestEvent.OnInvestmentPeriodTypeChange(
                            it
                        )
                    )
                },
                testTagAnnual = TestTags.COMPOUND_INTEREST.SELECTOR_INVEST_PERIOD,
                testTagMonthly = TestTags.COMPOUND_INTEREST.SELECTOR_INVEST_PERIOD
            )
        }

        Button(
            onClick = { onEvent(CompoundInterestEvent.OnCalculateClick) },
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.ButtonHeight)
                .testTag(TestTags.COMPOUND_INTEREST.BTN_CALCULATE),
            shape = RoundedCornerShape(Dimens.CornerMedium)
        ) {
            Text(stringResource(R.string.button_calculate))
        }
    }

    if (state.showDialog) {
        AlertDialog(
            onDismissRequest = { onEvent(CompoundInterestEvent.OnDismissDialog) },
            confirmButton = {
                TextButton(
                    onClick = { onEvent(CompoundInterestEvent.OnDismissDialog) },
                    modifier = Modifier.testTag(TestTags.COMPOUND_INTEREST.BTN_OK)
                ) {
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
                Column(
                    modifier = Modifier
                        .semantics { testTagsAsResourceId = true }
                        .testTag(TestTags.COMPOUND_INTEREST.DIALOG_RESULT),
                    verticalArrangement = Arrangement.spacedBy(Dimens.Space8)
                ) {
                    ResultRow(
                        stringResource(R.string.res_total_invested),
                        currencyFormatter.format(state.totalInvested),
                        TestTags.COMPOUND_INTEREST.TXT_TOTAL_INVESTED
                    )
                    ResultRow(
                        stringResource(R.string.res_total_with_interest),
                        currencyFormatter.format(state.totalWithInterest),
                        TestTags.COMPOUND_INTEREST.TXT_TOTAL_WITH_INTEREST
                    )
                    ResultRow(
                        stringResource(R.string.res_total_interest_only),
                        currencyFormatter.format(state.interestEarned),
                        TestTags.COMPOUND_INTEREST.TXT_INTEREST_EARNED,
                        isHighlight = true
                    )
                }
            }
        )
    }
}

@Composable
private fun ResultRow(
    label: String,
    value: String,
    testTag: String? = null,
    isHighlight: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.SemiBold,
            color = if (isHighlight) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurface,
            modifier = if (testTag != null) Modifier
                .testTag(testTag)
                .semantics { contentDescription = testTag }
            else Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CompoundInterestContentPreview() {
    FinanCalcBRTheme {
        CompoundInterestContent(
            state = CompoundInterestState(
                initialValue = "100000",
                monthlyValue = "50000",
                interestRate = "100",
                investmentPeriod = "12",
                totalInvested = 7000.0,
                totalWithInterest = 7899.27,
                interestEarned = 899.27,
                showDialog = false
            ),
            onEvent = {}
        )
    }
}