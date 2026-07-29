package com.financalcbr.app.domain.calculator

import com.financalcbr.app.utils.CltTaxTables
import javax.inject.Inject
import kotlin.math.max

data class VacationResult(
    val vacationDaysValue: Double,
    val oneThirdValue: Double,
    val abonoValue: Double,
    val oneThirdAbonoValue: Double,
    val thirteenAdvanceValue: Double,
    val inssDeduction: Double,
    val irrfDeduction: Double,
    val baseINSS: Double,
    val baseIRRF: Double,
    val grossIRRF: Double,
    val additionalReduction: Double,
    val totalEarnings: Double,
    val totalDeductions: Double,
    val netVacation: Double
)

class CalculateVacationUseCase @Inject constructor() {

    operator fun invoke(
        grossSalary: Double,
        vacationDays: Int,
        dependentsCount: Int,
        sellVacation: Boolean,
        advanceThirteen: Boolean
    ): VacationResult {
        val dailyRate = grossSalary / 30.0

        val vacationDaysValue = dailyRate * vacationDays
        val oneThirdValue = vacationDaysValue / 3.0

        // Abono pecuniário: venda de até 1/3 do período TOTAL de férias (30 dias = até 10 dias),
        // independente de quantos dias estão sendo efetivamente gozados nesta parcela
        // (art. 134, §2º da CLT). Não sofre incidência de INSS/IRRF.
        val soldDays = if (sellVacation) 10 else 0
        val abonoValue = dailyRate * soldDays
        val oneThirdAbonoValue = abonoValue / 3.0

        val thirteenAdvanceValue = if (advanceThirteen) grossSalary / 2.0 else 0.0

        // INSS e IRRF incidem sobre férias gozadas + 1/3 constitucional (não sobre o abono)
        val taxableForINSS = vacationDaysValue + oneThirdValue
        val inssDeduction = CltTaxTables.calculateInss(taxableForINSS)

        val grossTaxableIRRF = taxableForINSS
        val legalDeductions = inssDeduction + (dependentsCount * CltTaxTables.DEPENDENT_DEDUCTION)
        val irrfBase = if (legalDeductions > CltTaxTables.SIMPLIFIED_DEDUCTION) {
            max(0.0, grossTaxableIRRF - legalDeductions)
        } else {
            max(0.0, grossTaxableIRRF - CltTaxTables.SIMPLIFIED_DEDUCTION)
        }
        val grossIRRF = CltTaxTables.calculateGrossIrrf(irrfBase)

        // Redutor adicional do IRRF (Lei nº 15.270/2025), vigente desde jan/2026.
        // As faixas de R$5.000/R$7.350 são checadas sobre a base tributável das férias
        // (férias + 1/3), já que esta calculadora não tem visibilidade sobre outros
        // rendimentos pagos na mesma competência.
        val additionalReduction = CltTaxTables.calculateAdditionalReduction(grossTaxableIRRF, grossIRRF)
        val irrfDeduction = max(0.0, grossIRRF - additionalReduction)

        val totalEarnings = vacationDaysValue + oneThirdValue +
                abonoValue + oneThirdAbonoValue + thirteenAdvanceValue

        val totalDeductions = inssDeduction + irrfDeduction
        val netVacation = totalEarnings - totalDeductions

        return VacationResult(
            vacationDaysValue = vacationDaysValue,
            oneThirdValue = oneThirdValue,
            abonoValue = abonoValue,
            oneThirdAbonoValue = oneThirdAbonoValue,
            thirteenAdvanceValue = thirteenAdvanceValue,
            inssDeduction = inssDeduction,
            irrfDeduction = irrfDeduction,
            baseINSS = taxableForINSS,
            baseIRRF = irrfBase,
            grossIRRF = grossIRRF,
            additionalReduction = additionalReduction,
            totalEarnings = totalEarnings,
            totalDeductions = totalDeductions,
            netVacation = netVacation
        )
    }
}