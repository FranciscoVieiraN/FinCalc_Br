package com.financalcbr.app.ui.features.simple_interest

sealed interface SimpleInterestEvent {

    data class CapitalChanged(val value: String) : SimpleInterestEvent
    data class RateChanged(val value: String) : SimpleInterestEvent
    data class TimeChanged(val value: String) : SimpleInterestEvent
    object Calculate : SimpleInterestEvent

}