package com.financalcbr.app.domain.calculator

import com.financalcbr.app.ui.features.compound_interest.TimePeriod
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CalculateCompoundInterestUseCaseTest {

    private lateinit var calculateCompoundInterestUseCase: CalculateCompoundInterestUseCase

    @Before
    fun setUp() {
        calculateCompoundInterestUseCase = CalculateCompoundInterestUseCase()
    }

    @Test
    fun `calculate without monthly contributions`() {
        // P = 1000, i = 1% ao mês, t = 12 meses
        // A = 1000 * (1.01)^12 = 1126.825...
        val result = calculateCompoundInterestUseCase(
            initialValue = 1000.0,
            monthlyValue = 0.0,
            interestRate = 1.0,
            interestRatePeriod = TimePeriod.MONTHLY,
            investmentPeriod = 12,
            investmentPeriodType = TimePeriod.MONTHLY
        )

        assertEquals(1000.0, result.totalInvested, 0.01)
        assertEquals(1126.82, result.totalWithInterest, 0.01)
        assertEquals(126.82, result.interestEarned, 0.01)
    }

    @Test
    fun `calculate with monthly contributions`() {
        // P = 1000, PMT = 100, i = 1% ao mês, t = 12 meses
        // A_principal = 1000 * (1.01)^12 = 1126.825
        // A_contributions = 100 * ((1.01^12 - 1) / 0.01) = 100 * 12.6825 = 1268.25
        // Total = 1126.825 + 1268.25 = 2395.075
        val result = calculateCompoundInterestUseCase(
            initialValue = 1000.0,
            monthlyValue = 100.0,
            interestRate = 1.0,
            interestRatePeriod = TimePeriod.MONTHLY,
            investmentPeriod = 12,
            investmentPeriodType = TimePeriod.MONTHLY
        )

        assertEquals(2200.0, result.totalInvested, 0.01)
        assertEquals(2395.08, result.totalWithInterest, 0.01)
        assertEquals(195.08, result.interestEarned, 0.01)
    }

    @Test
    fun `calculate with annual interest rate and monthly period`() {
        // Taxa anual 12% -> Taxa mensal = (1 + 0.12)^(1/12) - 1 ≈ 0.9488%
        val result = calculateCompoundInterestUseCase(
            initialValue = 1000.0,
            monthlyValue = 0.0,
            interestRate = 12.0,
            interestRatePeriod = TimePeriod.ANNUAL,
            investmentPeriod = 12,
            investmentPeriodType = TimePeriod.MONTHLY
        )

        assertEquals(1000.0, result.totalInvested, 0.01)
        assertEquals(1120.0, result.totalWithInterest, 0.01)
        assertEquals(120.0, result.interestEarned, 0.01)
    }
}