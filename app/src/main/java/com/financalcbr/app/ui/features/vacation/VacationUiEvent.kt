package com.financalcbr.app.ui.features.vacation

sealed interface VacationUiEvent {
    data class OnGrossSalaryChanged(val value: String) : VacationUiEvent
    data class OnVacationDaysChanged(val value: String) : VacationUiEvent
    data class OnDependentsChanged(val value: String) : VacationUiEvent
    data object OnToggleSellVacation : VacationUiEvent
    data object OnToggleAdvanceThirteen : VacationUiEvent
    data object OnCalculateClicked : VacationUiEvent
    data object OnClearClicked : VacationUiEvent
}
