package com.financalcbr.app.ui.features.compound_interest


enum class TimePeriod {
    MONTHLY,
    ANNUAL
}

data class CompoundInterestState(
    val initialValue: String = "",
    val monthlyValue: String = "",
    val interestRate: String = "",
    val interestRatePeriod: TimePeriod = TimePeriod.MONTHLY,
    val investmentPeriod: String = "",
    val investmentPeriodType: TimePeriod = TimePeriod.MONTHLY,
    val totalInvested: Double = 0.0,
    val totalWithInterest: Double = 0.0,
    val interestEarned: Double = 0.0,
    val showDialog: Boolean = false
)