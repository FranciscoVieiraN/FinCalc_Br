package com.financalcbr.app.ui.features.vacation

import com.financalcbr.app.domain.calculator.VacationResult

data class VacationUiState(
    val grossSalary: String = "",
    val vacationDays: String = "30",
    val dependentsCount: String = "0",
    val sellVacation: Boolean = false,
    val advanceThirteen: Boolean = false,
    val vacationDaysValue: Double = 0.0,
    val oneThirdValue: Double = 0.0,
    val abonoValue: Double = 0.0,
    val oneThirdAbonoValue: Double = 0.0,
    val thirteenAdvanceValue: Double = 0.0,
    val inssDeduction: Double = 0.0,
    val irrfDeduction: Double = 0.0,
    val totalEarnings: Double = 0.0,
    val totalDeductions: Double = 0.0,
    val netVacation: Double = 0.0,
    val showResults: Boolean = false,
    val grossSalaryError: String? = null,
    val vacationDaysError: String? = null
) {
    fun withResult(result: VacationResult) = copy(
        vacationDaysValue = result.vacationDaysValue,
        oneThirdValue = result.oneThirdValue,
        abonoValue = result.abonoValue,
        oneThirdAbonoValue = result.oneThirdAbonoValue,
        thirteenAdvanceValue = result.thirteenAdvanceValue,
        inssDeduction = result.inssDeduction,
        irrfDeduction = result.irrfDeduction,
        totalEarnings = result.totalEarnings,
        totalDeductions = result.totalDeductions,
        netVacation = result.netVacation,
        showResults = true
    )
}
