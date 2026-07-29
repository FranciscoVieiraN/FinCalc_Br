package com.financalcbr.app.ui.features.net_salary

import androidx.lifecycle.ViewModel
import com.financalcbr.app.domain.calculator.CalculateNetSalaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NetSalaryViewModel @Inject constructor(
    private val calculateNetSalaryUseCase: CalculateNetSalaryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NetSalaryUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: NetSalaryUiEvent) {
        when (event) {
            is NetSalaryUiEvent.OnGrossSalaryChanged -> {
                _state.update { it.copy(grossSalary = event.value, grossSalaryError = null) }
            }
            is NetSalaryUiEvent.OnDependentsChanged -> {
                _state.update { it.copy(dependentsCount = event.value, dependentsError = null) }
            }
            is NetSalaryUiEvent.OnOtherDeductionsChanged -> {
                _state.update { it.copy(otherDeductions = event.value, otherDeductionsError = null) }
            }
            NetSalaryUiEvent.OnCalculateClicked -> {
                calculate()
            }
            NetSalaryUiEvent.OnClearClicked -> {
                _state.value = NetSalaryUiState()
            }
        }
    }

    private fun calculate() {
        val grossSalary = (_state.value.grossSalary.replace(",", ".").toDoubleOrNull() ?: 0.0)/ 100.0
        val dependents = _state.value.dependentsCount.toIntOrNull() ?: 0
        val otherDeductions = (_state.value.otherDeductions.replace(",", ".").toDoubleOrNull() ?: 0.0)/ 100.00

        if (grossSalary <= 0) {
            _state.update { it.copy(grossSalaryError = "Informe um salário válido") }
            return
        }

        val result = calculateNetSalaryUseCase(
            grossSalary = grossSalary,
            dependentsCount = dependents,
            otherDeductions = otherDeductions
        )

        _state.update {
            it.copy(
                grossSalaryVal = result.grossSalary,
                inssDeduction = result.inssDeduction,
                irrfDeduction = result.irrfDeduction,
                otherDeductionsVal = result.otherDeductions,
                netSalary = result.netSalary,
                showResults = true
            )
        }
    }
}
