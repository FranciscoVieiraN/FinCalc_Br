package com.financalcbr.app.domain.calculator

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateVacationUseCaseTest {

    private val useCase = CalculateVacationUseCase()

    @Test
    fun `vacation 30 days with R$ 2500 no abono no 13 no dependents should calculate with simplified IRRF`() {
        val result = useCase(
            grossSalary = 2500.0,
            vacationDays = 30,
            dependentsCount = 0,
            sellVacation = false,
            advanceThirteen = false
        )

        assertEquals(2500.0, result.vacationDaysValue, 0.01)
        assertEquals(833.33, result.oneThirdValue, 0.01)
        assertEquals(0.0, result.abonoValue, 0.01)
        assertEquals(0.0, result.thirteenAdvanceValue, 0.01)

        // INSS bracket 3 (12%, ded 111.40): (3333.33 * 0.12) - 111.40 = 288.60
        assertEquals(288.60, result.inssDeduction, 0.1)
        assertEquals(3333.33, result.baseINSS, 0.01)

        // Simplified deduction = 607.20 > legal = 288.60
        val expectedIRRFBase = 3333.33 - 607.20
        assertEquals(expectedIRRFBase, result.baseIRRF, 0.1)
        // Fully exempt: grossIRRF = 14.76, redutor = 14.76 → irrfFinal = 0.0
        assertEquals(0.0, result.irrfDeduction, 0.01)
    }

    @Test
    fun `vacation 20 days with R$ 3300 no abono no 13 no dependents`() {
        val result = useCase(
            grossSalary = 3300.0,
            vacationDays = 20,
            dependentsCount = 0,
            sellVacation = false,
            advanceThirteen = false
        )

        assertEquals(2200.0, result.vacationDaysValue, 0.01)
        assertEquals(733.33, result.oneThirdValue, 0.01)
        assertEquals(0.0, result.abonoValue, 0.01)
    }

    @Test
    fun `vacation 30 days with R$ 4000 selling abono no 13 no dependents`() {
        val result = useCase(
            grossSalary = 4000.0,
            vacationDays = 30,
            dependentsCount = 0,
            sellVacation = true,
            advanceThirteen = false
        )

        val expectedDaily = 4000.0 / 30.0
        assertEquals(4000.0, result.vacationDaysValue, 0.01)
        assertEquals(1333.33, result.oneThirdValue, 0.01)
        assertEquals(expectedDaily * 10, result.abonoValue, 0.01)
        assertEquals(expectedDaily * 10 / 3.0, result.oneThirdAbonoValue, 0.01)
        assertEquals(0.0, result.thirteenAdvanceValue, 0.01)

        assertEquals(10, (result.abonoValue / expectedDaily + 0.5).toInt())
    }

    @Test
    fun `vacation 30 days with R$ 5000 selling abono and 13th advance no dependents`() {
        val result = useCase(
            grossSalary = 5000.0,
            vacationDays = 30,
            dependentsCount = 0,
            sellVacation = true,
            advanceThirteen = true
        )

        assertEquals(5000.0, result.vacationDaysValue, 0.01)
        assertEquals(1666.67, result.oneThirdValue, 0.01)
        assertEquals(2500.0, result.thirteenAdvanceValue, 0.01)

        val dailyRate = 5000.0 / 30.0
        assertEquals(dailyRate * 10, result.abonoValue, 0.01)
        assertEquals(dailyRate * 10 / 3.0, result.oneThirdAbonoValue, 0.01)
    }

    @Test
    fun `vacation 15 days with R$ 2800 and 1 dependent no abono no 13`() {
        val result = useCase(
            grossSalary = 2800.0,
            vacationDays = 15,
            dependentsCount = 1,
            sellVacation = false,
            advanceThirteen = false
        )

        assertEquals(1400.0, result.vacationDaysValue, 0.01)
        assertEquals(466.67, result.oneThirdValue, 0.01)

        val taxableINSS = 1400.0 + 466.67
        assertEquals(taxableINSS, result.baseINSS, 0.01)
    }

    @Test
    fun `vacation 30 days with R$ 12000 no abono no 13 should hit INSS ceiling and IRRF top bracket`() {
        val result = useCase(
            grossSalary = 12000.0,
            vacationDays = 30,
            dependentsCount = 0,
            sellVacation = false,
            advanceThirteen = false
        )

        assertEquals(12000.0, result.vacationDaysValue, 0.01)
        assertEquals(4000.0, result.oneThirdValue, 0.01)

        // 2026 INSS ceiling = 8475.55, bracket 4 (14%, ded 198.49)
        // INSS = (8475.55 * 0.14) - 198.49 = 988.09
        assertEquals(988.09, result.inssDeduction, 0.1)
    }

    @Test
    fun `vacation 10 days with R$ 3500 abono and 13th and 2 dependents`() {
        val result = useCase(
            grossSalary = 3500.0,
            vacationDays = 10,
            dependentsCount = 2,
            sellVacation = true,
            advanceThirteen = true
        )

        val dailyRate = 3500.0 / 30.0
        assertEquals(dailyRate * 10, result.vacationDaysValue, 0.01)
        assertEquals(dailyRate * 10 / 3.0, result.oneThirdValue, 0.01)
        assertEquals(1750.0, result.thirteenAdvanceValue, 0.01)

        assertEquals(dailyRate * 10, result.abonoValue, 0.01)
        assertEquals(dailyRate * 10 / 3.0, result.oneThirdAbonoValue, 0.01)

        assert(result.netVacation > 0)
    }

    @Test
    fun `vacation 30 days with R$ 2000 and 2 dependents should be fully IRRF exempt`() {
        val result = useCase(
            grossSalary = 2000.0,
            vacationDays = 30,
            dependentsCount = 2,
            sellVacation = false,
            advanceThirteen = false
        )

        assertEquals(0.0, result.irrfDeduction, 0.01)
        assert(result.netVacation > 0)
    }

    @Test
    fun `vacation 30 days with R$ 3000 no abono no 13 no dependents should calculate with simplified IRRF`() {
        val result = useCase(
            grossSalary = 3000.0,
            vacationDays = 30,
            dependentsCount = 0,
            sellVacation = false,
            advanceThirteen = false
        )

        assertEquals(3000.0, result.vacationDaysValue, 0.01)
        assertEquals(1000.0, result.oneThirdValue, 0.01)

        // INSS bracket 3 (12%, ded 111.40): (4000 * 0.12) - 111.40 = 368.60
        assertEquals(368.60, result.inssDeduction, 0.1)

        // Simplified deduction = 607.20 > legal = 368.60
        val expectedIRRFBase = 4000.0 - 607.20
        assertEquals(expectedIRRFBase, result.baseIRRF, 0.1)
        // Fully exempt via redutor
        assertEquals(0.0, result.irrfDeduction, 0.01)
    }
}
