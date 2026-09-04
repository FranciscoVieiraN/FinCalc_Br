package com.financalcbr.app.ui.features.net_salary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.financalcbr.app.ui.common.TestTags
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NetSalaryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun allInputFieldsAndButtons_areDisplayed() {
        composeTestRule.setContent {
            NetSalaryContent(
                state = NetSalaryUiState(),
                onEvent = {}
            )
        }

        composeTestRule.onNodeWithTag(TestTags.NET_SALARY.INPUT_GROSS_SALARY).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.NET_SALARY.INPUT_DEPENDENTS).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.NET_SALARY.INPUT_OTHER_DEDUCTIONS).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.NET_SALARY.BTN_CLEAR).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.NET_SALARY.BTN_CALCULATE).assertIsDisplayed()
    }

    @Test
    fun calculate_displaysResultCard() {
        var state by mutableStateOf(NetSalaryUiState())

        composeTestRule.setContent {
            NetSalaryContent(
                state = state,
                onEvent = { event ->
                    when (event) {
                        NetSalaryUiEvent.OnCalculateClicked -> {
                            state = state.copy(
                                grossSalaryVal = 5000.0,
                                inssDeduction = 501.51,
                                irrfDeduction = 0.0,
                                netSalary = 4498.49,
                                showResults = true
                            )
                        }
                        is NetSalaryUiEvent.OnGrossSalaryChanged -> {
                            state = state.copy(grossSalary = event.value)
                        }
                        is NetSalaryUiEvent.OnDependentsChanged -> {
                            state = state.copy(dependentsCount = event.value)
                        }
                        else -> {}
                    }
                }
            )
        }

        composeTestRule.onNodeWithTag(TestTags.NET_SALARY.BTN_CALCULATE).performClick()

        composeTestRule.onNodeWithTag(TestTags.NET_SALARY.CARD_RESULT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.NET_SALARY.TXT_INSS_DEDUCTION).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.NET_SALARY.TXT_IRRF_DEDUCTION).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.NET_SALARY.TXT_NET_SALARY).assertIsDisplayed()
    }

    @Test
    fun netSalaryValue_displaysFormattedCurrency() {
        var state by mutableStateOf(NetSalaryUiState())

        composeTestRule.setContent {
            NetSalaryContent(
                state = state,
                onEvent = { event ->
                    if (event is NetSalaryUiEvent.OnCalculateClicked) {
                        state = state.copy(
                            grossSalaryVal = 5000.0,
                            inssDeduction = 501.51,
                            irrfDeduction = 0.0,
                            netSalary = 4498.49,
                            otherDeductionsVal = 0.0,
                            showResults = true
                        )
                    }
                }
            )
        }

        composeTestRule.onNodeWithTag(TestTags.NET_SALARY.BTN_CALCULATE).performClick()

        val text = composeTestRule
            .onNodeWithTag(TestTags.NET_SALARY.TXT_NET_SALARY)
            .fetchSemanticsNode()
            .config.getOrNull(SemanticsProperties.Text)
            ?.joinToString("") { it.text }

        assertNotNull(text)
        assertTrue(text!!.contains("R$"))
        assertTrue(text!!.contains("4.498,49"))
    }

    @Test
    fun clearButton_resetsResults() {
        var state by mutableStateOf(NetSalaryUiState())

        composeTestRule.setContent {
            NetSalaryContent(
                state = state,
                onEvent = { event ->
                    when (event) {
                        NetSalaryUiEvent.OnCalculateClicked -> {
                            state = state.copy(
                                grossSalaryVal = 5000.0,
                                inssDeduction = 501.51,
                                irrfDeduction = 0.0,
                                netSalary = 4498.49,
                                showResults = true
                            )
                        }
                        NetSalaryUiEvent.OnClearClicked -> {
                            state = NetSalaryUiState()
                        }
                        else -> {}
                    }
                }
            )
        }

        composeTestRule.onNodeWithTag(TestTags.NET_SALARY.BTN_CALCULATE).performClick()
        composeTestRule.onNodeWithTag(TestTags.NET_SALARY.CARD_RESULT).assertIsDisplayed()

        composeTestRule.onNodeWithTag(TestTags.NET_SALARY.BTN_CLEAR).performClick()

        composeTestRule.onNodeWithTag(TestTags.NET_SALARY.CARD_RESULT).assertDoesNotExist()
    }
}