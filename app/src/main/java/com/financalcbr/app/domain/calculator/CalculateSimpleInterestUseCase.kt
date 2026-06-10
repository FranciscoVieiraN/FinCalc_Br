package com.financalcbr.app.domain.calculator

import javax.inject.Inject

class CalculateSimpleInterestUseCase @Inject constructor() {

    operator fun invoke(
        capital: Double,
        ratePercent: Double,
        time: Double
    ): SimpleInterestResult {

        val rate = ratePercent / 100

        val interest = capital * rate * time

        val total = capital + interest

        return SimpleInterestResult(
            interest = interest,
            totalAmount = total
        )
    }
}

data class SimpleInterestResult(
    val interest: Double,
    val totalAmount: Double
)