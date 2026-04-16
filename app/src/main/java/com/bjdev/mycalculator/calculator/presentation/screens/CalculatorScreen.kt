package com.bjdev.mycalculator.calculator.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bjdev.mycalculator.calculator.presentation.viewmodels.CalculatorViewModel

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = viewModel()
) {
    val expression by viewModel.expression.collectAsStateWithLifecycle()
    val result by viewModel.result.collectAsStateWithLifecycle()

    // El error de "Suspicious indentation" desaparece al alinear Column con los val de arriba
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Bottom
    ) {
        // --- Display ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Cambiado a weight para que ocupe el espacio sobrante arriba
                .background(Color.Black)
        ) {
            Text(
                text = if (result.isNotEmpty()) result else expression.ifEmpty { "0" },
                color = Color.White,
                fontSize = 50.sp,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                style = MaterialTheme.typography.displayMedium // Cambiado a display para números grandes
            )
        }

        // --- Teclado ---
        val buttons = listOf(
            listOf("C", "/", "x", "⌫"),
            listOf("7", "8", "9", "-"),
            listOf("4", "5", "6", "+"),
            listOf("1", "2", "3", "="),
            listOf("0", ".", "(", ")")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { label ->
                    Button(
                        onClick = {
                            when (label) {
                                "=" -> viewModel.onCalculate()
                                "C" -> viewModel.onClear()
                                "⌫" -> viewModel.onDelete()
                                else -> viewModel.onInput(label)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(85.dp)
                            .padding(4.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = label,
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
        // Espacio extra al final para que no pegue con el borde de la pantalla
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorScreen()
}