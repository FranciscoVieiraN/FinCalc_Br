package com.financalcbr.app.domain.calculator

import javax.inject.Inject

class CalculateOvertimeUseCase @Inject constructor() {

    operator fun invoke(
        salary: String,
        monthlyHours: String,
        extraHoursCount: String,
        bonusPercentage: String
    ): OvertimeResult {
        val salaryRaw = (salary.toDoubleOrNull() ?: 0.0) / 100.0
        val mHours = monthlyHours.toIntOrNull() ?: 220
        val eHoursCount = extraHoursCount.toIntOrNull() ?: 0
        val bPercentage = (bonusPercentage.toDoubleOrNull() ?: 0.0) / 100.0

        val commonHourValue = if (mHours > 0) salaryRaw / mHours else 0.0
        val extraHourValue = commonHourValue * (1 + (bPercentage / 100))
        val totalAmount = extraHourValue * eHoursCount

        return OvertimeResult(
            commonHourValue = commonHourValue,
            extraHourValue = extraHourValue,
            totalAmount = totalAmount
        )
    }

    data class OvertimeResult(
        val commonHourValue: Double,
        val extraHourValue: Double,
        val totalAmount: Double
    )
}
