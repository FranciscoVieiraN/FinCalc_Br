package com.financalcbr.app.ui.features.compound_interest

sealed interface CompoundInterestEvent {
    data class OnInitialValueChange(val value: String) : CompoundInterestEvent
    data class OnMonthlyValueChange(val value: String) : CompoundInterestEvent
    data class OnInterestRateChange(val value: String) : CompoundInterestEvent
    data class OnInterestRatePeriodChange(val period: TimePeriod) : CompoundInterestEvent
    data class OnInvestmentPeriodChange(val value: String) : CompoundInterestEvent
    data class OnInvestmentPeriodTypeChange(val period: TimePeriod) : CompoundInterestEvent
    data object OnCalculateClick : CompoundInterestEvent
    data object OnDismissDialog : CompoundInterestEvent
}