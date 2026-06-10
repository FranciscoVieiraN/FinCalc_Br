package com.financalcbr.app.utils

object ConstantsUtils {

    const val TAG = "App_Finan_Calc_BR"
    const val ADD: Char = '+'
    const val SUB: Char = '-'
    const val DIVD: Char = '÷'
    const val MULT: Char = '×'
    const val PORCENT: Char = '%'
    const val EQUAL: Char = '='
    val OPERATORS: Set<Char> = setOf(ADD, SUB, MULT, DIVD, PORCENT)
    val INVALID_FINAL_OPERATOR: Set<Char> = setOf(ADD, SUB, MULT, DIVD)

}