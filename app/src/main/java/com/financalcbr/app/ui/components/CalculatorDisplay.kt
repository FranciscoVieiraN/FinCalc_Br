package com.financalcbr.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.financalcbr.app.utils.getScreenWidth

@Composable
fun CalculatorDisplay(
    expression: String,
    modifier: Modifier = Modifier
) {
    val fontSize = when {
        expression.length > 16 -> 32.sp
        expression.length > 12 -> 40.sp
        else -> 48.sp
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = (0.05 * getScreenWidth()).dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        BasicTextField(
            value = expression,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = fontSize,
                color = Color.Black,
                textAlign = TextAlign.End
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
