package com.financalcbr.app.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.financalcbr.app.R
import com.financalcbr.app.ui.features.compound_interest.TimePeriod


@Composable
fun PeriodSelector(
    selectedPeriod: TimePeriod,
    onPeriodSelected: (TimePeriod) -> Unit,
    modifier: Modifier = Modifier,
    testTagAnnual: String? = null,
    testTagMonthly: String? = null
) {
    Row(modifier = modifier) {
        FilterChip(
            selected = selectedPeriod == TimePeriod.ANNUAL,
            onClick = { onPeriodSelected(TimePeriod.ANNUAL) },
            label = { Text(stringResource(R.string.period_annual)) },
            modifier = if (testTagAnnual != null) Modifier.testTag(testTagAnnual) else Modifier
        )

        Spacer(modifier = Modifier.width(2.dp))

        FilterChip(
            selected = selectedPeriod == TimePeriod.MONTHLY,
            onClick = { onPeriodSelected(TimePeriod.MONTHLY) },
            label = { Text(stringResource(R.string.period_monthly)) },
            modifier = if (testTagMonthly != null) Modifier.testTag(testTagMonthly) else Modifier
        )
    }
}