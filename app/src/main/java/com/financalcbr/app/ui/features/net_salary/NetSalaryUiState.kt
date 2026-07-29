package com.financalcbr.app.ui.features.net_salary

data class NetSalaryUiState(
    val grossSalary: String = "",
    val dependentsCount: String = "",
    val otherDeductions: String = "",
    val grossSalaryVal: Double = 0.0,
    val inssDeduction: Double = 0.0,
    val irrfDeduction: Double = 0.0,
    val otherDeductionsVal: Double = 0.0,
    val netSalary: Double = 0.0,
    val showResults: Boolean = false,
    val grossSalaryError: String? = null,
    val dependentsError: String? = null,
    val otherDeductionsError: String? = null
)
