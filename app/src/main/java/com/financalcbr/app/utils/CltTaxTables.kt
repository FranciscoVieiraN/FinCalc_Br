package com.financalcbr.app.utils

import kotlin.math.max
import kotlin.math.min

/**
 * Fonte única de verdade para as tabelas de tributação de um trabalhador CLT
 * no regime vigente a partir de janeiro/2026.
 *
 * Fontes oficiais:
 * - INSS: Portaria Interministerial MPS/MF nº 13/2026 (faixas, alíquotas e teto).
 * - IRRF (tabela progressiva): inalterada desde 2023 (Lei nº 9.250/1995).
 * - Redutor adicional do IRRF: Lei nº 15.270/2025, vigente a partir de 01/01/2026,
 *   que isenta rendimentos mensais de até R$ 5.000,00 e reduz parcialmente o
 *   imposto para rendimentos entre R$ 5.000,01 e R$ 7.350,00. A Receita Federal
 *   confirma que a mesma lógica se aplica também ao 13º salário.
 *
 * ⚠️ IMPORTANTE: o teto e as faixas do INSS são reajustados TO-DO ANO (geralmente
 * em janeiro). A tabela do IRRF e o redutor só mudam por lei. Revisar esta classe
 * anualmente contra a portaria vigente.
 */
object CltTaxTables {

    // ---------------------------------------------------------------------
    // INSS — Portaria Interministerial MPS/MF nº 13/2026
    // ---------------------------------------------------------------------

    const val INSS_CEILING = 8475.55

    private data class InssBracket(
        val upperLimit: Double,
        val rate: Double,
        val deduction: Double
    )

    private val inssBrackets = listOf(
        InssBracket(upperLimit = 1621.00, rate = 0.075, deduction = 0.0),
        InssBracket(upperLimit = 2902.84, rate = 0.09, deduction = 24.32),
        InssBracket(upperLimit = 4354.27, rate = 0.12, deduction = 111.40),
        InssBracket(upperLimit = Double.MAX_VALUE, rate = 0.14, deduction = 198.49)
    )

    /**
     * Calcula o desconto de INSS pelo metodo 'parcela a deduzir', já respeitando
     * o teto de contribuição. [salary] é o salário de contribuição (base), não
     * necessariamente o salário bruto integral (ex: em férias, é o valor de
     * férias + 1/3, sem o abono pecuniário).
     */
    fun calculateInss(salary: Double): Double {
        val base = min(salary, INSS_CEILING)
        val bracket = inssBrackets.first { base <= it.upperLimit }
        return (base * bracket.rate) - bracket.deduction
    }

    // ---------------------------------------------------------------------
    // IRRF — tabela progressiva mensal (inalterada desde 2023)
    // ---------------------------------------------------------------------

    const val DEPENDENT_DEDUCTION = 189.59
    const val SIMPLIFIED_DEDUCTION = 607.20

    private data class IrrfBracket(
        val upperLimit: Double,
        val rate: Double,
        val deduction: Double
    )

    private val irrfBrackets = listOf(
        IrrfBracket(upperLimit = 2428.80, rate = 0.0, deduction = 0.0),
        IrrfBracket(upperLimit = 2826.65, rate = 0.075, deduction = 182.16),
        IrrfBracket(upperLimit = 3751.05, rate = 0.15, deduction = 394.16),
        IrrfBracket(upperLimit = 4664.68, rate = 0.225, deduction = 675.49),
        IrrfBracket(upperLimit = Double.MAX_VALUE, rate = 0.275, deduction = 908.73)
    )

    /** Calcula o IRRF "bruto" pela tabela progressiva tradicional, antes do redutor de 2026. */
    fun calculateGrossIrrf(base: Double): Double {
        val bracket = irrfBrackets.first { base <= it.upperLimit }
        return max(0.0, (base * bracket.rate) - bracket.deduction)
    }

    /**
     * Redutor adicional do IRRF (Lei nº 15.270/2025), vigente a partir de jan/2026.
     *
     * - Até R$ 5.000,00 de rendimento tributável: isenção total (redutor = imposto apurado).
     * - De R$ 5.000,01 a R$ 7.350,00: redução parcial e decrescente.
     * - Acima de R$ 7.350,00: sem redução.
     *
     * O redutor é limitado ao imposto apurado (não gera "imposto negativo").
     *
     * @param taxableIncome rendimento tributável mensal usado para checar as faixas
     * (ex: salário bruto no cálculo de salário líquido; base de férias + 1/3 no cálculo de férias).
     * @param grossIrrf o IRRF calculado pela tabela tradicional, antes do redutor.
     */
    fun calculateAdditionalReduction(taxableIncome: Double, grossIrrf: Double): Double {
        val rawReduction = when {
            taxableIncome <= 5000.00 -> grossIrrf
            taxableIncome <= 7350.00 -> max(0.0, 978.62 - (0.133145 * taxableIncome))
            else -> 0.0
        }
        return min(rawReduction, grossIrrf)
    }
}