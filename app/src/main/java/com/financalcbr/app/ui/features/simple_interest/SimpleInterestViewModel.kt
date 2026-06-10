package com.financalcbr.app.ui.features.simple_interest

import androidx.lifecycle.ViewModel
import com.financalcbr.app.domain.calculator.CalculateSimpleInterestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SimpleInterestViewModel @Inject constructor(
    private val calculateSimpleInterest: CalculateSimpleInterestUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SimpleInterestState())

    val state = _state.asStateFlow()

    fun onEvent(event: SimpleInterestEvent) {

        when (event) {

            is SimpleInterestEvent.CapitalChanged -> {
                updateCapital(event.value)
            }

            is SimpleInterestEvent.RateChanged -> {
                updateRate(event.value)
            }

            is SimpleInterestEvent.TimeChanged -> {
                updateTime(event.value)
            }

            SimpleInterestEvent.Calculate -> {
                calculate()
            }
        }
    }

    private fun updateCapital(value: String) {
        _state.update {
            it.copy(
                capital = value,
                isCalculateEnabled = canCalculate(
                    value,
                    it.rate,
                    it.time
                )
            )
        }
    }

    private fun updateRate(value: String) {
        _state.update {
            it.copy(
                rate = value,
                isCalculateEnabled = canCalculate(
                    it.capital,
                    value,
                    it.time
                )
            )
        }
    }

    private fun updateTime(value: String) {
        _state.update {
            it.copy(
                time = value,
                isCalculateEnabled = canCalculate(
                    it.capital,
                    it.rate,
                    value
                )
            )
        }
    }

    private fun canCalculate(
        capital: String,
        rate: String,
        time: String
    ): Boolean {

        return capital.isNotBlank() &&
                rate.isNotBlank() &&
                time.isNotBlank()
    }

    private fun calculate() {

        try {

            val capital =
                _state.value.capital
                    .replace(",", ".")
                    .toDouble()

            val rate =
                _state.value.rate
                    .replace(",", ".")
                    .toDouble()

            val time =
                _state.value.time
                    .replace(",", ".")
                    .toDouble()

            val result =
                calculateSimpleInterest(
                    capital,
                    rate,
                    time
                )

            _state.update {

                it.copy(
                    interestResult =
                        format(result.interest),

                    totalResult =
                        format(result.totalAmount)
                )
            }

        } catch (e: Exception) {

            _state.update {
                it.copy(
                    interestResult = "Erro",
                    totalResult = "Erro"
                )
            }
        }
    }

    private fun format(value: Double): String {

        return if (value % 1 == 0.0) {
            value.toLong().toString()
        } else {
            "%.2f".format(value)
        }
    }
}