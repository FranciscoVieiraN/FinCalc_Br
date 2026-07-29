package com.financalcbr.app.domain.calculator

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateNetSalaryUseCaseTest {

    private val useCase = CalculateNetSalaryUseCase()

    @Test
    fun `calculate net salary with R$ 5000 - should be exempt`() {
        val grossSalary = 5000.0
        val dependents = 0
        val otherDeductions = 0.0

        val result = useCase(grossSalary, dependents, otherDeductions)

        // Salary <= 5000 should have 0.0 final IRRF
        assertEquals(0.0, result.irrfDeduction, 0.01)
        assertEquals(0.0, result.effectiveRate, 0.01)
    }

    @Test
    fun `calculate net salary with R$ 6000 - should test gradual reduction`() {
        val grossSalary = 6000.0
        val dependents = 0
        val otherDeductions = 0.0

        val result = useCase(grossSalary, dependents, otherDeductions)

        // INSS: (6000 * 0.14) - 198.49 = 840 - 198.49 = 641.51
        // Base IRRF = 6000 - 641.51 = 5358.49
        // Gross IRRF = (5358.49 * 0.275) - 908.73 = 1473.58 - 908.73 = 564.85
        // Reduction = max(0, 978.62 - (0.133145 * 6000)) = 179.75
        // Final IRRF = 564.85 - 179.75 = 385.10

        assertEquals(564.85, result.grossIRRF, 0.1)
        assertEquals(179.75, result.additionalReduction, 0.1)
        assertEquals(385.10, result.irrfDeduction, 0.1)
    }

    @Test
    fun `calculate net salary with R$ 10000 - should test full table`() {
        val grossSalary = 10000.0
        val dependents = 0
        val otherDeductions = 0.0

        val result = useCase(grossSalary, dependents, otherDeductions)

        // INSS Ceiling: 8475.55 → INSS = (8475.55 * 0.14) - 198.49 = 988.09
        // Base = 10000 - 988.09 = 9011.91
        // Gross IRRF (Table 27.5%): (9011.91 * 0.275) - 908.73 = 2478.28 - 908.73 = 1569.55
        // No reduction for > 7350
        
        assertEquals(1569.55, result.irrfDeduction, 0.1)
        assertEquals(0.0, result.additionalReduction, 0.01)
        assertEquals("LEGAL", result.discountMethod)
    }

    @Test
    fun `calculate net salary with R$ 3000 - should be exempt`() {
        val grossSalary = 3000.0
        val dependents = 0
        val otherDeductions = 0.0

        val result = useCase(grossSalary, dependents, otherDeductions)

        assertEquals(0.0, result.irrfDeduction, 0.01)
    }
}
