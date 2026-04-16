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

//Refactorizar código para separar por responsabilidades
@Composable
fun CalculatorScreen(
    viewModel : CalculatorViewModel = viewModel()
) {
    val expression by viewModel.expression.collectAsStateWithLifecycle()
    val result by viewModel.result.collectAsStateWithLifecycle()


        Column(//Contenedor principal
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            verticalArrangement = Arrangement.Bottom
        ) {

            // Display
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color.Black)
            ) {
                Text(
                    text = if (result.isNotEmpty()) result else expression.ifEmpty { "0" },
                    color = Color.White,
                    fontSize = 50.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp),
                    style = MaterialTheme.typography.titleSmall
                )
            }

            // Teclado
            val buttons = listOf(
                listOf("C", "/", "x","⌫"),
                listOf("7", "8", "9", "-"),
                listOf("4", "5", "6", "+"),
                listOf("1", "2", "3", "="),
                listOf("0", ".", "(", ")")
            )

            buttons.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { label ->
                        Button(
                            onClick = {
                                when (label) {
                                    "=" -> viewModel.onCalculate()
                                    "C" -> viewModel.onClear()
                                    "⌫"-> viewModel.onDelete()
                                    else -> viewModel.onInput(label)
                                }

                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(90.dp)
                                .padding(4.dp)
                        ) {
                            Text(
                                text = label,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorScreen()
}
