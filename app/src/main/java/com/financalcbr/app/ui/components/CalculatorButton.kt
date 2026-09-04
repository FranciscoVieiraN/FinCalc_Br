package com.financalcbr.app.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorButton(label: String, modifier: Modifier = Modifier, testTag: String? = null, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = if (testTag != null) modifier.testTag(testTag) else modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Text(
            text = label,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}