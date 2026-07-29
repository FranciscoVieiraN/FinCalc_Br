package com.financalcbr.app.domain.calculator

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class CalculateSimpleInterestUseCaseTest {

    private val useCase = CalculateSimpleInterestUseCase()

    @Test
    fun `calculate simple interest with standard values`() {
        val result = useCase(1000.0, 10.0, 2.0)
        assertNotNull(result)
        assertEquals(200.0, result.interest, 0.001)
        assertEquals(1200.0, result.totalAmount, 0.001)
    }

    @Test
    fun `calculate simple interest with zero duration`() {
        val result = useCase(1000.0, 10.0, 0.0)
        assertNotNull(result)
        assertEquals(0.0, result.interest, 0.001)
        assertEquals(1000.0, result.totalAmount, 0.001)
    }

    @Test
    fun `calculate simple interest with zero capital`() {
        val result = useCase(0.0, 10.0, 2.0)
        assertNotNull(result)
        assertEquals(0.0, result.interest, 0.001)
        assertEquals(0.0, result.totalAmount, 0.001)
    }

    @Test
    fun `calculate simple interest with high rate`() {
        val result = useCase(1000.0, 50.0, 2.0)
        assertNotNull(result)
        assertEquals(1000.0, result.interest, 0.001)
        assertEquals(2000.0, result.totalAmount, 0.001)
    }
}
