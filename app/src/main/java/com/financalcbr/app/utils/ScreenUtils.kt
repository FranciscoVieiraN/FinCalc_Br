package com.financalcbr.app.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun getScreenHeight(): Float {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp.toFloat()
}

@Composable
fun getScreenWidth(): Float {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp.toFloat()
}
