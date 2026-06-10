package com.financalcbr.app.ui.features.compound_interest

import androidx.lifecycle.ViewModel
import com.financalcbr.app.domain.calculator.CalculateCompoundInterestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CompoundInterestViewModel @Inject constructor(
    private val calculateCompoundInterestUseCase: CalculateCompoundInterestUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CompoundInterestState())
    val state = _state.asStateFlow()

    fun onEvent(event: CompoundInterestEvent) {
        when (event) {
            is CompoundInterestEvent.OnInitialValueChange -> {
                _state.update { it.copy(initialValue = event.value.filter { c -> c.isDigit() }) }
            }
            is CompoundInterestEvent.OnMonthlyValueChange -> {
                _state.update { it.copy(monthlyValue = event.value.filter { c -> c.isDigit() }) }
            }
            is CompoundInterestEvent.OnInterestRateChange -> {
                _state.update { it.copy(interestRate = event.value.filter { c -> c.isDigit() }) }
            }
            is CompoundInterestEvent.OnInterestRatePeriodChange -> {
                _state.update { it.copy(interestRatePeriod = event.period) }
            }
            is CompoundInterestEvent.OnInvestmentPeriodChange -> {
                _state.update { it.copy(investmentPeriod = event.value.filter { c -> c.isDigit() }) }
            }
            is CompoundInterestEvent.OnInvestmentPeriodTypeChange -> {
                _state.update { it.copy(investmentPeriodType = event.period) }
            }
            CompoundInterestEvent.OnCalculateClick -> {
                calculateCompoundInterest()
            }
            CompoundInterestEvent.OnDismissDialog -> {
                _state.update { it.copy(showDialog = false) }
            }
        }
    }

    private fun calculateCompoundInterest() {
        val currentState = _state.value

        val initialValue = currentState.initialValue.toDoubleOrNull()?.div(100.0) ?: 0.0
        val monthlyValue = currentState.monthlyValue.toDoubleOrNull()?.div(100.0) ?: 0.0
        val interestRate = currentState.interestRate.toDoubleOrNull()?.div(100.0) ?: 0.0
        val investmentPeriod = currentState.investmentPeriod.toIntOrNull() ?: 0

        val result = calculateCompoundInterestUseCase(
            initialValue = initialValue,
            monthlyValue = monthlyValue,
            interestRate = interestRate,
            interestRatePeriod = currentState.interestRatePeriod,
            investmentPeriod = investmentPeriod,
            investmentPeriodType = currentState.investmentPeriodType
        )

        _state.update {
            it.copy(
                totalInvested = result.totalInvested,
                totalWithInterest = result.totalWithInterest,
                interestEarned = result.interestEarned,
                showDialog = true
            )
        }
    }
}