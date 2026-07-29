package com.financalcbr.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.financalcbr.app.ui.features.calculator.CalculatorScreen
import com.financalcbr.app.ui.features.compound_interest.CompoundInterestScreen
import com.financalcbr.app.ui.features.net_salary.NetSalaryScreen
import com.financalcbr.app.ui.features.overtime.OvertimeScreen
import com.financalcbr.app.ui.features.simple_interest.SimpleInterestScreen
import com.financalcbr.app.ui.features.vacation.VacationScreen


@Composable
fun FinanCalcBRNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = FinanCalcBRRoute.Calculator,
    ) {
        composable<FinanCalcBRRoute.Calculator> {
            CalculatorScreen()
        }
        composable<FinanCalcBRRoute.SimpleInterest> {
            SimpleInterestScreen()
        }
        composable<FinanCalcBRRoute.CompoundInterest> {
            CompoundInterestScreen()
        }
        composable<FinanCalcBRRoute.NetSalary> {
            NetSalaryScreen()
        }
        composable<FinanCalcBRRoute.Overtime> {
            OvertimeScreen()
        }
        composable<FinanCalcBRRoute.Vacation> {
            VacationScreen()
        }
    }
}
