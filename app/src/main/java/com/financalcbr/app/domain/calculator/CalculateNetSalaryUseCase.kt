package com.financalcbr.app.domain.calculator

import com.financalcbr.app.utils.CltTaxTables
import javax.inject.Inject
import kotlin.math.max

data class NetSalaryResult(
    val grossSalary: Double,
    val inssDeduction: Double,
    val irrfDeduction: Double,
    val otherDeductions: Double,
    val netSalary: Double,
    val baseIRRF: Double,
    val discountMethod: String,
    val effectiveRate: Double,
    val grossIRRF: Double,
    val additionalReduction: Double
)

class CalculateNetSalaryUseCase @Inject constructor() {

    operator fun invoke(
        grossSalary: Double,
        dependentsCount: Int,
        otherDeductions: Double
    ): NetSalaryResult {
        val inss = CltTaxTables.calculateInss(grossSalary)

        // Passo 1: Definir a Base de Cálculo do IRPF (maior entre dedução legal e simplificada)
        val totalDependentsDeduction = dependentsCount * CltTaxTables.DEPENDENT_DEDUCTION
        val legalDeductions = inss + totalDependentsDeduction + otherDeductions

        val (baseIRRF, discountMethod) = if (legalDeductions > CltTaxTables.SIMPLIFIED_DEDUCTION) {
            (max(0.0, grossSalary - legalDeductions)) to "LEGAL"
        } else {
            (max(0.0, grossSalary - CltTaxTables.SIMPLIFIED_DEDUCTION)) to "SIMPLIFICADO"
        }

        // Passo 2: Calcular o Imposto Bruto pela tabela progressiva tradicional
        val grossIRRF = CltTaxTables.calculateGrossIrrf(baseIRRF)

        // Passo 3: Aplicar o redutor adicional do IRRF (Lei nº 15.270/2025, vigente desde 2026)
        val additionalReduction = CltTaxTables.calculateAdditionalReduction(grossSalary, grossIRRF)
        val irrfFinal = max(0.0, grossIRRF - additionalReduction)

        val netSalary = grossSalary - inss - irrfFinal - otherDeductions
        val effectiveRate = if (grossSalary > 0) (irrfFinal / grossSalary) * 100 else 0.0

        return NetSalaryResult(
            grossSalary = grossSalary,
            inssDeduction = inss,
            irrfDeduction = irrfFinal,
            otherDeductions = otherDeductions,
            netSalary = netSalary,
            baseIRRF = baseIRRF,
            discountMethod = discountMethod,
            effectiveRate = effectiveRate,
            grossIRRF = grossIRRF,
            additionalReduction = additionalReduction
        )
    }
}