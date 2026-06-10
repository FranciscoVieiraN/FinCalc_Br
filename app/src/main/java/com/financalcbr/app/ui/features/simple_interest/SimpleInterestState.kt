package com.financalcbr.app.ui.features.simple_interest

data class SimpleInterestState(

    val capital: String = "",
    val rate: String = "",
    val time: String = "",

    val interestResult: String = "",
    val totalResult: String = "",

    val isCalculateEnabled: Boolean = false

)