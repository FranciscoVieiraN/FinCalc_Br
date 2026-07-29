package com.financalcbr.app.ui.features.overtime


data class OvertimeState(
    val salary: String = "",
    val monthlyHours: String = "220",
    val extraHoursCount: String = "1",
    val bonusPercentage: String = "5000",
    val commonHourValue: Double = 0.0,
    val extraHourValue: Double = 0.0,
    val totalAmount: Double = 0.0,
    val showDialog: Boolean = false,
    val isSalaryError: Boolean = false
)
