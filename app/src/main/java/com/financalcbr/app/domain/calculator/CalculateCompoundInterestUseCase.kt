package com.financalcbr.app.domain.calculator

import com.financalcbr.app.ui.features.compound_interest.TimePeriod
import javax.inject.Inject
import kotlin.math.pow

class CalculateCompoundInterestUseCase @Inject constructor() {

    operator fun invoke(
        initialValue: Double,
        monthlyValue: Double,
        interestRate: Double,
        interestRatePeriod: TimePeriod,
        investmentPeriod: Int,
        investmentPeriodType: TimePeriod
    ): CompoundInterestResult {
        // Converte a taxa para decimal mensal
        val monthlyRate = if (interestRatePeriod == TimePeriod.ANNUAL) {
            (1.0 + interestRate / 100.0).pow(1.0 / 12.0) - 1.0
        } else {
            interestRate / 100.0
        }

        // Converte o período para meses
        val totalMonths = if (investmentPeriodType == TimePeriod.ANNUAL) {
            investmentPeriod * 12
        } else {
            investmentPeriod
        }

        if (totalMonths <= 0) {
            return CompoundInterestResult(initialValue, initialValue, 0.0)
        }

        // Fórmula de Juros Compostos com aportes mensais:
        // A = P(1 + i)^n + PMT * [(1 + i)^n - 1] / i
        val compoundFactor = (1.0 + monthlyRate).pow(totalMonths)
        
        val finalPrincipal = initialValue * compoundFactor
        val finalContributions = if (monthlyRate > 0) {
            monthlyValue * (compoundFactor - 1) / monthlyRate
        } else {
            monthlyValue * totalMonths.toDouble()
        }

        val totalWithInterest = finalPrincipal + finalContributions
        val totalInvested = initialValue + (monthlyValue * totalMonths)
        val interestEarned = totalWithInterest - totalInvested

        return CompoundInterestResult(
            totalInvested = totalInvested,
            totalWithInterest = totalWithInterest,
            interestEarned = interestEarned
        )
    }
}

data class CompoundInterestResult(
    val totalInvested: Double,
    val totalWithInterest: Double,
    val interestEarned: Double
)