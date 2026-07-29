package com.financalcbr.app.ui.features.net_salary

sealed interface NetSalaryUiEvent {
    data class OnGrossSalaryChanged(val value: String) : NetSalaryUiEvent
    data class OnDependentsChanged(val value: String) : NetSalaryUiEvent
    data class OnOtherDeductionsChanged(val value: String) : NetSalaryUiEvent
    data object OnCalculateClicked : NetSalaryUiEvent
    data object OnClearClicked : NetSalaryUiEvent
}
