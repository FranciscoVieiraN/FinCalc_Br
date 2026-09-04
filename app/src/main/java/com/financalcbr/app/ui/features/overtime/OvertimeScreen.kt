package com.financalcbr.app.ui.features.overtime

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
import androidx.compose.runtime.remember
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
import com.financalcbr.app.ui.theme.Dimens
import com.financalcbr.app.ui.theme.FinanCalcBRTheme
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OvertimeScreen(
    viewModel: OvertimeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    OvertimeContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OvertimeContent(
    state: OvertimeState,
    onEvent: (OvertimeEvent) -> Unit
) {
    val currencyFormatter = remember {
        NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.Space16)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(Dimens.Space16)
    ) {
        Text(
            text = stringResource(R.string.title_overtime),
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = state.salary,
            onValueChange = { onEvent(OvertimeEvent.OnSalaryChange(it)) },
            label = { Text(stringResource(R.string.label_gross_salary)) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.OVERTIME.INPUT_SALARY),
            shape = RoundedCornerShape(Dimens.CornerMedium),
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.Space8)
        ) {
            OutlinedTextField(
                value = state.monthlyHours,
                onValueChange = { onEvent(OvertimeEvent.OnMonthlyHoursChange(it)) },
                label = { Text(stringResource(R.string.label_monthly_hours)) },
                modifier = Modifier
                    .weight(1f)
                    .testTag(TestTags.OVERTIME.INPUT_MONTHLY_HOURS),
                shape = RoundedCornerShape(Dimens.CornerMedium),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimens.Space4)
            ) {
                TextButton(
                    onClick = { onEvent(OvertimeEvent.OnMonthlyHoursChange("220")) },
                    modifier = Modifier.testTag(TestTags.OVERTIME.BTN_PRESET_220H)
                ) {
                    Text(stringResource(R.string.label_hours_220))
                }
                TextButton(
                    onClick = { onEvent(OvertimeEvent.OnMonthlyHoursChange("180")) },
                    modifier = Modifier.testTag(TestTags.OVERTIME.BTN_PRESET_180H)
                ) {
                    Text(stringResource(R.string.label_hours_180))
                }
            }
        }

        OutlinedTextField(
            value = state.extraHoursCount,
            onValueChange = { onEvent(OvertimeEvent.OnExtraHoursCountChange(it)) },
            label = { Text(stringResource(R.string.label_extra_hours_count)) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.OVERTIME.INPUT_EXTRA_HOURS_COUNT),
            shape = RoundedCornerShape(Dimens.CornerMedium),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.Space8)
        ) {
            OutlinedTextField(
                value = state.bonusPercentage,
                onValueChange = { onEvent(OvertimeEvent.OnBonusPercentageChange(it)) },
                label = { Text(stringResource(R.string.label_bonus_percentage)) },
                modifier = Modifier
                    .weight(1f)
                    .testTag(TestTags.OVERTIME.INPUT_BONUS_PERCENTAGE),
                shape = RoundedCornerShape(Dimens.CornerMedium),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = PercentageVisualTransformation(),
                singleLine = true
            )
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.Space4)) {
                TextButton(
                    onClick = { onEvent(OvertimeEvent.OnBonusPercentageChange("5000")) },
                    modifier = Modifier.testTag(TestTags.OVERTIME.BTN_PRESET_50)
                ) {
                    Text(stringResource(R.string.label_percentage_50))
                }
                TextButton(
                    onClick = { onEvent(OvertimeEvent.OnBonusPercentageChange("10000")) },
                    modifier = Modifier.testTag(TestTags.OVERTIME.BTN_PRESET_100)
                ) {
                    Text(stringResource(R.string.label_percentage_100))
                }
            }
        }

        Button(
            onClick = { onEvent(OvertimeEvent.OnCalculateClick) },
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.ButtonHeight)
                .testTag(TestTags.OVERTIME.BTN_CALCULATE),
            shape = RoundedCornerShape(Dimens.CornerMedium),
            enabled = state.salary.isNotBlank()
        ) {
            Text(stringResource(R.string.button_calculate))
        }
    }

    if (state.showDialog) {
        AlertDialog(
            onDismissRequest = { onEvent(OvertimeEvent.OnDismissDialog) },
            confirmButton = {
                TextButton(
                    onClick = { onEvent(OvertimeEvent.OnDismissDialog) },
                    modifier = Modifier.testTag(TestTags.OVERTIME.BTN_OK)
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
                        .testTag(TestTags.OVERTIME.DIALOG_RESULT),
                    verticalArrangement = Arrangement.spacedBy(Dimens.Space8)
                ) {
                    ResultRow(stringResource(R.string.res_common_hour), currencyFormatter.format(state.commonHourValue), TestTags.OVERTIME.TXT_COMMON_HOUR)
                    ResultRow(stringResource(R.string.res_extra_hour_value), currencyFormatter.format(state.extraHourValue), TestTags.OVERTIME.TXT_EXTRA_HOUR_VALUE)
                    ResultRow(stringResource(R.string.res_total_gross), currencyFormatter.format(state.totalAmount), TestTags.OVERTIME.TXT_TOTAL_GROSS, isHighlight = true)
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
fun OvertimeContentPreview() {
    FinanCalcBRTheme {
        OvertimeContent(
            state = OvertimeState(
                salary = "350000",
                monthlyHours = "220",
                extraHoursCount = "10",
                bonusPercentage = "5000",
                commonHourValue = 15.91,
                extraHourValue = 23.86,
                totalAmount = 238.64,
                showDialog = false
            ),
            onEvent = {}
        )
    }
}