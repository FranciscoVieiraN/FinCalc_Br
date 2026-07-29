package com.financalcbr.app.domain.calculator

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CalculateOvertimeUseCaseTest {

    private lateinit var useCase: CalculateOvertimeUseCase

    @Before
    fun setup() {
        useCase = CalculateOvertimeUseCase()
    }

    @Test
    fun `deve calcular horas extras com bonus de 50 porcento corretamente`() {
        // Salário: 2200,00 (representado como "220000")
        // Horas Mensais: 220
        // Quantidade de Horas Extras: 10
        // Porcentagem de Bônus: 50,00% (representado como "5000")
        
        val result = useCase(
            salary = "220000",
            monthlyHours = "220",
            extraHoursCount = "10",
            bonusPercentage = "5000"
        )

        assertEquals(10.0, result.commonHourValue, 0.01)
        assertEquals(15.0, result.extraHourValue, 0.01)
        assertEquals(150.0, result.totalAmount, 0.01)
    }

    @Test
    fun `deve calcular horas extras com bonus de 100 porcento corretamente`() {
        // Salário: 1320,00 (representado como "132000")
        // Horas Mensais: 220
        // Quantidade de Horas Extras: 2
        // Porcentagem de Bônus: 100,00% (representado como "10000")

        val result = useCase(
            salary = "132000",
            monthlyHours = "220",
            extraHoursCount = "2",
            bonusPercentage = "10000"
        )

        assertEquals(6.0, result.commonHourValue, 0.01)
        assertEquals(12.0, result.extraHourValue, 0.01)
        assertEquals(24.0, result.totalAmount, 0.01)
    }

    @Test
    fun `deve retornar zeros quando os campos estiverem vazios`() {
        val result = useCase(
            salary = "",
            monthlyHours = "",
            extraHoursCount = "",
            bonusPercentage = ""
        )

        assertEquals(0.0, result.commonHourValue, 0.01)
        assertEquals(0.0, result.extraHourValue, 0.01)
        assertEquals(0.0, result.totalAmount, 0.01)
    }

    @Test
    fun `deve lidar com divisao por zero se horas mensais for zero`() {
        val result = useCase(
            salary = "200000",
            monthlyHours = "0",
            extraHoursCount = "10",
            bonusPercentage = "5000"
        )

        assertEquals(0.0, result.commonHourValue, 0.01)
        assertEquals(0.0, result.extraHourValue, 0.01)
        assertEquals(0.0, result.totalAmount, 0.01)
    }
}
