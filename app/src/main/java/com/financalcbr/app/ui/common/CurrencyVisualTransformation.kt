package com.financalcbr.app.ui.common

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.NumberFormat
import java.util.Locale

class CurrencyVisualTransformation(
    private val locale: Locale = Locale("pt", "BR")
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text

        // Formatação do número
        // Se estiver vazio, tratamos como 0 para gerar o "R$ 0,00"
        val number = originalText.toLongOrNull() ?: 0L
        val formattedValue = NumberFormat.getCurrencyInstance(locale).format(number / 100.0)
        val newText = AnnotatedString(formattedValue)

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                // Sempre coloca o cursor no final do texto formatado
                return newText.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                // SEGURANÇA: Garante que o mapeamento nunca aponte para fora do texto original
                // Se o texto original for "123" (tamanho 3), o retorno nunca será maior que 3.
                return originalText.length
            }
        }

        return TransformedText(newText, offsetMapping)
    }
}