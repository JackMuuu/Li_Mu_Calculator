package com.example.calculator_hw2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculator_hw2.ui.theme.Calculator_hw2Theme
import android.widget.Button
import android.widget.EditText


class MainActivity : ComponentActivity() {

    private lateinit var display: EditText
    private var currentInput: String = ""
    private var operator: String = ""
    private var firstValue: Double = 0.0
    private var secondValue: Double = 0.0
    private var isOperatorPressed = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        // Allow input via the keyboard directly into the EditText
        enableDirectTyping()

        // Set button click listeners for numbers and operations12
        setNumberButtons()
        setOperationButtons()
        setClearButton()
        setEqualsButton()
    }

    // Allow users to type numbers and operations via the keyboard directly
    private fun enableDirectTyping() {
        display.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                currentInput = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // If the user directly typed into the EditText, update currentInput
                currentInput = s.toString()
            }
        })
    }

    // number buttons (0-9, dot)
    private fun setNumberButtons() {
        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { button ->
                if (isOperatorPressed) {
                    display.setText("")
                    isOperatorPressed = false
                }
                val input = (button as Button).text.toString()
                currentInput += input
                display.setText(currentInput)
            }
        }
    }

    // operation buttons (+, -, *, /)
    private fun setOperationButtons() {
        val operators = listOf(
            R.id.btnPlus to "+",
            R.id.btnMinus to "-",
            R.id.btnMultiply to "*",
            R.id.btnDivide to "÷"
        )

        operators.forEach { (id, op) ->
            findViewById<Button>(id).setOnClickListener {
                operator = op
                firstValue = currentInput.toDoubleOrNull() ?: 0.0
                isOperatorPressed = true
            }
        }
    }

    // clear (AC)
    private fun setClearButton() {
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            display.setText("0.0")
            currentInput = ""
            firstValue = 0.0
            secondValue = 0.0
            operator = ""
            isOperatorPressed = false
        }
    }

    // equals (=)
    private fun setEqualsButton() {
        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            secondValue = currentInput.toDoubleOrNull() ?: 0.0
            val result = calculateResult(firstValue, secondValue, operator)
            display.setText(result.toString())
            currentInput = result.toString()
            isOperatorPressed = true
        }
    }

    // perform the arithmetic operations
    private fun calculateResult(firstValue: Double, secondValue: Double, operator: String): Double {
        return when (operator) {
            "+" -> firstValue + secondValue
            "-" -> firstValue - secondValue
            "*" -> firstValue * secondValue
            "÷" -> if (secondValue != 0.0) {
                firstValue / secondValue
            } else {
                display.setText("Error")
                0.0
            }
            else -> 0.0
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Calculator_hw2Theme {
        Greeting("Android")
    }
}