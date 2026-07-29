package com.financalcbr.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.financalcbr.app.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NavDrawer(
    onDestinationClicked: (FinanCalcBRRoute) -> Unit
) {
    ModalDrawerSheet {
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier.padding(16.dp),
            fontSize = 20.sp
        )

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.title_calculator)) },
            selected = false,
            onClick = { onDestinationClicked(FinanCalcBRRoute.Calculator) }
        )

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.title_simple_interest)) },
            selected = false,
            onClick = { onDestinationClicked(FinanCalcBRRoute.SimpleInterest) }
        )

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.title_compound_interest)) },
            selected = false,
            onClick = { onDestinationClicked(FinanCalcBRRoute.CompoundInterest) }
        )

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.title_overtime)) },
            selected = false,
            onClick = { onDestinationClicked(FinanCalcBRRoute.Overtime) }
        )

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.title_net_salary)) },
            selected = false,
            onClick = { onDestinationClicked(FinanCalcBRRoute.NetSalary) }
        )

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.title_vacation)) },
            selected = false,
            onClick = { onDestinationClicked(FinanCalcBRRoute.Vacation) }
        )
    }
}
