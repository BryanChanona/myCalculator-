package com.bjdev.mycalculator.calculator.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.bjdev.mycalculator.calculator.domain.evaluator.evaluatePostFix
import com.bjdev.mycalculator.calculator.domain.evaluator.infixToPostfix
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel : ViewModel() {

    private val _expression = MutableStateFlow("")
    val expression = _expression.asStateFlow()

    private val _result = MutableStateFlow("")
    val result = _result.asStateFlow()

    fun onInput(input: String) {
        if (_result.value.isNotEmpty()) {
            _expression.value = ""
            _result.value = ""
        }

        val normalizedInput = if (input == "x") "*" else input
        _expression.value += normalizedInput
    }

    fun onClear() {
        _expression.value = ""
        _result.value = ""
    }
    fun onDelete() {
        if (_expression.value.isNotEmpty()) {
            _expression.value = _expression.value.dropLast(1)
        }
    }


    fun onCalculate() {
        try {
            val postfix = infixToPostfix(_expression.value)
            val evaluation = evaluatePostFix(postfix)
            _result.value = evaluation.toString()
        } catch (e: Exception) {
            _result.value = "Error"
        }
    }
}
