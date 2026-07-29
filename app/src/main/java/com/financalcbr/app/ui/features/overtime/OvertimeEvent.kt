package com.financalcbr.app.ui.features.overtime


sealed interface OvertimeEvent {
    data class OnSalaryChange(val value: String) : OvertimeEvent
    data class OnMonthlyHoursChange(val value: String) : OvertimeEvent
    data class OnExtraHoursCountChange(val value: String) : OvertimeEvent
    data class OnBonusPercentageChange(val value: String) : OvertimeEvent
    data object OnCalculateClick : OvertimeEvent
    data object OnDismissDialog : OvertimeEvent
}
