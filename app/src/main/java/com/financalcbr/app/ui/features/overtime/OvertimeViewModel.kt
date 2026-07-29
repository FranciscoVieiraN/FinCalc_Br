package com.financalcbr.app.ui.features.overtime

import androidx.lifecycle.ViewModel
import com.financalcbr.app.domain.calculator.CalculateOvertimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class OvertimeViewModel @Inject constructor(
    private val calculateOvertimeUseCase: CalculateOvertimeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OvertimeState())
    val state = _state.asStateFlow()

    fun onEvent(event: OvertimeEvent) {
        when (event) {
            is OvertimeEvent.OnSalaryChange -> {
                _state.update { current ->
                    current.copy(
                        salary = event.value.filter { char -> char.isDigit() },
                        isSalaryError = false
                    )
                }
            }
            is OvertimeEvent.OnMonthlyHoursChange -> {
                _state.update { current ->
                    current.copy(monthlyHours = event.value.filter { char -> char.isDigit() })
                }
            }
            is OvertimeEvent.OnExtraHoursCountChange -> {
                _state.update { current ->
                    current.copy(extraHoursCount = event.value.filter { char -> char.isDigit() })
                }
            }
            is OvertimeEvent.OnBonusPercentageChange -> {
                _state.update { current ->
                    current.copy(bonusPercentage = event.value.filter { char -> char.isDigit() })
                }
            }
            OvertimeEvent.OnCalculateClick -> {
                calculateOvertime()
            }
            OvertimeEvent.OnDismissDialog -> {
                _state.update { current -> current.copy(showDialog = false) }
            }
        }
    }

    private fun calculateOvertime() {
        val currentState = _state.value

        val salaryValue = currentState.salary.toDoubleOrNull() ?: 0.0
        if (currentState.salary.isBlank() || salaryValue <= 0.0) {
            _state.update { current -> current.copy(isSalaryError = true) }
            return
        }

        val result = calculateOvertimeUseCase(
            salary = currentState.salary,
            monthlyHours = currentState.monthlyHours,
            extraHoursCount = currentState.extraHoursCount,
            bonusPercentage = currentState.bonusPercentage
        )

        _state.update { current ->
            current.copy(
                commonHourValue = result.commonHourValue,
                extraHourValue = result.extraHourValue,
                totalAmount = result.totalAmount,
                showDialog = true,
                isSalaryError = false
            )
        }
    }
}
