package com.bjdev.mycalculator.calculator.domain.evaluator

import java.util.ArrayDeque

fun evaluatePostFix(tokens: List<String>): Double {
    val stack = ArrayDeque<Double>()

    for (token in tokens) {
        when (token) {
            "+", "-", "*", "/" -> {
                if (stack.size < 2) {
                    throw IllegalArgumentException("Expresión inválida")
                }

                val b = stack.removeLast()
                val a = stack.removeLast()

                val result = when (token) {
                    "+" -> a + b
                    "-" -> a - b
                    "*" -> a * b
                    "/" -> {
                        if (b == 0.0) throw ArithmeticException("División por cero")
                        a / b
                    }
                    else -> error("Operador no soportado")
                }

                stack.addLast(result)
            }
            else -> {
                stack.addLast(token.toDouble())
            }
        }
    }

    if (stack.size != 1) {
        throw IllegalArgumentException("Expresión inválida")
    }

    return stack.removeLast()
}
