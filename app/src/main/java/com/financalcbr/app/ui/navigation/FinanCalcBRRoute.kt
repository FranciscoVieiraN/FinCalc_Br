package com.financalcbr.app.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface FinanCalcBRRoute {
    @Serializable
    data object Calculator : FinanCalcBRRoute

    @Serializable
    data object CompoundInterest : FinanCalcBRRoute

    @Serializable
    data object SimpleInterest : FinanCalcBRRoute

    @Serializable
    data object SalarioLiquido : FinanCalcBRRoute
}
