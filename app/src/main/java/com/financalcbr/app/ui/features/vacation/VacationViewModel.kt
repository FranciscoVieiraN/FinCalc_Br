package com.financalcbr.app.ui.features.vacation

import androidx.lifecycle.ViewModel
import com.financalcbr.app.domain.calculator.CalculateVacationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class VacationViewModel @Inject constructor(
    private val calculateVacationUseCase: CalculateVacationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(VacationUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: VacationUiEvent) {
        when (event) {
            is VacationUiEvent.OnGrossSalaryChanged -> {
                _state.update {
                    it.copy(
                        grossSalary = event.value.filter { char -> char.isDigit() },
                        grossSalaryError = null
                    )
                }
            }

            is VacationUiEvent.OnVacationDaysChanged -> {
                _state.update {
                    it.copy(
                        vacationDays = event.value.filter { it.isDigit() },
                        vacationDaysError = null
                    )
                }
            }

            is VacationUiEvent.OnDependentsChanged -> {
                _state.update { it.copy(dependentsCount = event.value.filter { it.isDigit() }) }
            }

            VacationUiEvent.OnToggleSellVacation -> {
                _state.update { it.copy(sellVacation = !it.sellVacation) }
            }

            VacationUiEvent.OnToggleAdvanceThirteen -> {
                _state.update { it.copy(advanceThirteen = !it.advanceThirteen) }
            }

            VacationUiEvent.OnCalculateClicked -> calculate()
            VacationUiEvent.OnClearClicked -> _state.value = VacationUiState()
        }
    }

    private fun calculate() {
        val grossSalary =
            (_state.value.grossSalary.replace(",", ".").toDoubleOrNull() ?: 0.0) / 100.0
        val vacationDays = _state.value.vacationDays.toIntOrNull() ?: 0
        val dependents = _state.value.dependentsCount.toIntOrNull() ?: 0

        var hasError = false
        if (grossSalary <= 0) {
            _state.update { it.copy(grossSalaryError = "Informe um salário válido") }
            hasError = true
        }
        if (vacationDays !in 1..30) {
            _state.update { it.copy(vacationDaysError = "Informe de 1 a 30 dias") }
            hasError = true
        }
        if (hasError) return

        val result = calculateVacationUseCase(
            grossSalary = grossSalary,
            vacationDays = vacationDays,
            dependentsCount = dependents,
            sellVacation = _state.value.sellVacation,
            advanceThirteen = _state.value.advanceThirteen
        )

        _state.update { it.withResult(result) }
    }
}
