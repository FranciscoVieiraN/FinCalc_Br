package com.financalcbr.app.ui.features.simple_interest

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.financalcbr.app.ui.common.CurrencyVisualTransformation
import com.financalcbr.app.ui.common.PercentageVisualTransformation
import java.text.NumberFormat
import java.util.Locale

@Composable
fun SimpleInterestScreen(
    viewModel: SimpleInterestViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Juros Simples",
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.capital,
            visualTransformation = CurrencyVisualTransformation(),
            singleLine = true,
            onValueChange = {
                viewModel.onEvent(
                    SimpleInterestEvent.CapitalChanged(it)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Capital (R$)") }
        )

        OutlinedTextField(
            value = state.rate,
            visualTransformation = PercentageVisualTransformation(),
            singleLine = true,
            onValueChange = {
                viewModel.onEvent(
                    SimpleInterestEvent.RateChanged(it)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Taxa (%)") }
        )

        OutlinedTextField(
            value = state.time,
            onValueChange = {
                viewModel.onEvent(
                    SimpleInterestEvent.TimeChanged(it)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Tempo") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.onEvent(
                    SimpleInterestEvent.Calculate
                )
            },
            enabled = state.isCalculateEnabled
        ) {
            Text("Calcular")
        }

        Spacer(modifier = Modifier.height(24.dp))
        if (state.interestResult.isNotBlank()) {
            Text("Juros: ${currencyFormatter.format(state.interestResult.toDoubleOrNull()?:0.0)}")
        }
        if (state.totalResult.isNotBlank()) {
            Text("Montante: ${currencyFormatter.format(state.totalResult.toDoubleOrNull()?:0.0)}")
        }

    }
}