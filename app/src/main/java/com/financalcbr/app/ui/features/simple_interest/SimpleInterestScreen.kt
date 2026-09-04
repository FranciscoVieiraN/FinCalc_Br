package com.financalcbr.app.ui.features.simple_interest

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.financalcbr.app.ui.common.PercentageVisualTransformation
import com.financalcbr.app.ui.common.TestTags
import com.financalcbr.app.ui.theme.Dimens
import com.financalcbr.app.ui.theme.FinanCalcBRTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun SimpleInterestScreen(
    viewModel: SimpleInterestViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    SimpleInterestContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun SimpleInterestContent(
    state: SimpleInterestState,
    onEvent: (SimpleInterestEvent) -> Unit
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
            text = stringResource(R.string.title_simple_interest),
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = state.capital,
            visualTransformation = CurrencyVisualTransformation(),
            singleLine = true,
            onValueChange = { onEvent(SimpleInterestEvent.CapitalChanged(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.SIMPLE_INTEREST.INPUT_CAPITAL),
            shape = RoundedCornerShape(Dimens.CornerMedium),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.label_capital)) }
        )

        OutlinedTextField(
            value = state.rate,
            visualTransformation = PercentageVisualTransformation(),
            singleLine = true,
            onValueChange = { onEvent(SimpleInterestEvent.RateChanged(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.SIMPLE_INTEREST.INPUT_RATE),
            shape = RoundedCornerShape(Dimens.CornerMedium),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.label_rate)) }
        )

        OutlinedTextField(
            value = state.time,
            onValueChange = { onEvent(SimpleInterestEvent.TimeChanged(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.SIMPLE_INTEREST.INPUT_TIME),
            shape = RoundedCornerShape(Dimens.CornerMedium),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.label_time)) }
        )

        Button(
            onClick = { onEvent(SimpleInterestEvent.Calculate) },
            enabled = state.isCalculateEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.ButtonHeight)
                .testTag(TestTags.SIMPLE_INTEREST.BTN_CALCULATE),
            shape = RoundedCornerShape(Dimens.CornerMedium)
        ) {
            Text(stringResource(R.string.button_calculate))
        }

        if (state.interestResult.isNotBlank() || state.totalResult.isNotBlank()) {
            SimpleInterestResultCard(state, currencyFormatter)
        }
    }
}

@Composable
private fun SimpleInterestResultCard(state: SimpleInterestState, formatter: NumberFormat) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                text = stringResource(R.string.calculation_result_title),
                style = MaterialTheme.typography.titleMedium
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            Text(
                text = "${stringResource(R.string.result_interest_label)} ${formatter.format(state.interestResult.toDoubleOrNull() ?: 0.0)}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .testTag(TestTags.SIMPLE_INTEREST.TXT_INTEREST_RESULT)
                    .semantics { contentDescription = TestTags.SIMPLE_INTEREST.TXT_INTEREST_RESULT }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.result_amount_label),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = formatter.format(state.totalResult.toDoubleOrNull() ?: 0.0),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .testTag(TestTags.SIMPLE_INTEREST.TXT_TOTAL_RESULT)
                        .semantics { contentDescription = TestTags.SIMPLE_INTEREST.TXT_TOTAL_RESULT }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleInterestContentPreview() {
    FinanCalcBRTheme {
        SimpleInterestContent(
            state = SimpleInterestState(
                capital = "100000",
                rate = "150",
                time = "12",
                interestResult = "1500",
                totalResult = "11500",
                isCalculateEnabled = true
            ),
            onEvent = {}
        )
    }
}