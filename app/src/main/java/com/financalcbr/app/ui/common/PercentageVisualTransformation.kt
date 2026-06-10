package com.financalcbr.app.ui.common

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class PercentageVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text

        // Formatação para 0,00 %
        // Transformamos a string de dígitos em um número decimal (ex: "125" -> 1,25)
        val number = originalText.toLongOrNull() ?: 0L

        // Configura o formatador para o padrão brasileiro (vírgula como decimal)
        val symbols = DecimalFormatSymbols(Locale("pt", "BR"))
        val formatter = DecimalFormat("#,##0.00", symbols)

        val formattedValue = formatter.format(number / 100.0)
        val newText = AnnotatedString("$formattedValue %")

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                // Sempre mantém o cursor no final, antes do símbolo de %
                // ou no final total para evitar confusão visual
                return formattedValue.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                // SEGURANÇA CRÍTICA:
                // Garante que o retorno nunca seja maior que o tamanho do texto original.
                // Isso evita o erro "IllegalStateException: OffsetMapping.transformedToOriginal"
                return originalText.length
            }
        }

        return TransformedText(newText, offsetMapping)
    }
}