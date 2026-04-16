package com.bjdev.mycalculator.calculator.domain.evaluator

import java.util.ArrayDeque

fun infixToPostfix(expression: String): List<String> {
    val output = mutableListOf<String>()
    val operators = ArrayDeque<Char>()
    var number = ""

    val tokens = expression.replace(" ", "").toCharArray()

    for (char in tokens) {
        if (char.isDigit() || char == '.') {
            number += char
        } else {
            if (number.isNotEmpty()) {
                output.add(number)
                number = ""
            }

            when (char) {
                '(' -> operators.addLast(char)

                ')' -> {
                    while (operators.isNotEmpty() && operators.last() != '(') {
                        output.add(operators.removeLast().toString())
                    }
                    if (operators.isEmpty()) {
                        throw IllegalArgumentException("Paréntesis no balanceados")
                    }
                    operators.removeLast()
                }

                '+', '-', '*', '/' -> {
                    while (
                        operators.isNotEmpty() &&
                        precedence(operators.last()) >= precedence(char)
                    ) {
                        output.add(operators.removeLast().toString())
                    }
                    operators.addLast(char)
                }

                else -> throw IllegalArgumentException("Carácter inválido: $char")
            }
        }
    }

    if (number.isNotEmpty()) {
        output.add(number)
    }

    while (operators.isNotEmpty()) {
        val op = operators.removeLast()
        if (op == '(' || op == ')') {
            throw IllegalArgumentException("Paréntesis no balanceados")
        }
        output.add(op.toString())
    }

    return output
}

fun precedence(op: Char): Int =
    when (op) {
        '+', '-' -> 1
        '*', '/' -> 2
        else -> 0
    }
