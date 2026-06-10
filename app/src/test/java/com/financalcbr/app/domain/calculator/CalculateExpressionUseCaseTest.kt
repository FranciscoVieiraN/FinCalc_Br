package com.financalcbr.app.domain.calculator

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CalculateExpressionUseCaseTest {

    private lateinit var useCase: CalculateExpressionUseCase

    @Before
    fun setup() {
        useCase = CalculateExpressionUseCase()
    }

    @Test
    fun `soma simples`() {
        val result = useCase("2+3")
        assertEquals("5", result)
    }

    @Test
    fun `subtracao simples`() {
        val result = useCase("10-4")
        assertEquals("6", result)
    }

    @Test
    fun `multiplicacao simples`() {
        val result = useCase("5×2")
        assertEquals("10", result)
    }

    @Test
    fun `divisao simples`() {
        val result = useCase("8÷2")
        assertEquals("4", result)
    }

    @Test
    fun `precedencia matematica`() {
        val result = useCase("2+3×4")
        assertEquals("14", result)
    }

    @Test
    fun `numeros decimais`() {
        val result = useCase("2,5+1,5")
        assertEquals("4", result)
    }

    @Test
    fun `porcentagem soma`() {
        val result = useCase("100+10%")
        assertEquals("110", result)
    }

    @Test
    fun `porcentagem subtracao`() {
        val result = useCase("100-5%")
        assertEquals("95", result)
    }

    @Test
    fun `porcentagem multiplicacao`() {
        val result = useCase("50×10%")
        assertEquals("5", result)
    }

    @Test
    fun `porcentagem divisao`() {
        val result = useCase("50÷10%")
        assertEquals("500", result)
    }

    @Test
    fun `ignora operador final`() {
        val result = useCase("10+")
        assertEquals("10", result)
    }

    @Test
    fun `expressao vazia`() {
        val result = useCase("")
        assertEquals("0", result)
    }


}
