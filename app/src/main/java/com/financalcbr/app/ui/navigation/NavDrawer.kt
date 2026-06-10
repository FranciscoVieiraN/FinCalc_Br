package com.financalcbr.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NavDrawer(
    onDestinationClicked: (FinanCalcBRRoute) -> Unit
) {
    ModalDrawerSheet {
        Text(
            text = "FinanCalc BR",
            modifier = Modifier.padding(16.dp),
            fontSize = 20.sp
        )

        NavigationDrawerItem(
            label = { Text("Calculadora") },
            selected = false,
            onClick = { onDestinationClicked(FinanCalcBRRoute.Calculator) }
        )

        NavigationDrawerItem(
            label = { Text("Juros Simples") },
            selected = false,
            onClick = { onDestinationClicked(FinanCalcBRRoute.SimpleInterest) }
        )

        NavigationDrawerItem(
            label = { Text("Juros Composto") },
            selected = false,
            onClick = { onDestinationClicked(FinanCalcBRRoute.CompoundInterest) }
        )

        NavigationDrawerItem(
            label = { Text("Salário Líquido") },
            selected = false,
            onClick = { onDestinationClicked(FinanCalcBRRoute.SalarioLiquido) }
        )
    }
}
