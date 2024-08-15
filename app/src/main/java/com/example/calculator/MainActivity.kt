package com.example.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityMainBinding
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }
    private var equation = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListeners()
    }

    private fun setupListeners() {
        binding.apply {
            zero.setOnClickListener(this@MainActivity)
            one.setOnClickListener(this@MainActivity)
            two.setOnClickListener(this@MainActivity)
            three.setOnClickListener(this@MainActivity)
            four.setOnClickListener(this@MainActivity)
            five.setOnClickListener(this@MainActivity)
            six.setOnClickListener(this@MainActivity)
            seven.setOnClickListener(this@MainActivity)
            eight.setOnClickListener(this@MainActivity)
            nine.setOnClickListener(this@MainActivity)
            plus.setOnClickListener(this@MainActivity)
            minus.setOnClickListener(this@MainActivity)
            multiply.setOnClickListener(this@MainActivity)
            divide.setOnClickListener(this@MainActivity)
            dot.setOnClickListener(this@MainActivity)
            back.setOnClickListener(this@MainActivity)
            AC.setOnClickListener(this@MainActivity)
            equal.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.zero -> addNumber("0")
            R.id.one -> addNumber("1")
            R.id.two -> addNumber("2")
            R.id.three -> addNumber("3")
            R.id.four -> addNumber("4")
            R.id.five -> addNumber("5")
            R.id.six -> addNumber("6")
            R.id.seven -> addNumber("7")
            R.id.eight -> addNumber("8")
            R.id.nine -> addNumber("9")
            R.id.plus -> addOperator("+")
            R.id.minus -> addOperator("-")
            R.id.multiply -> addOperator("*")
            R.id.divide -> addOperator("/")
            R.id.dot -> addDot()
            R.id.back -> removeLastCharacter()
            R.id.AC -> clearEquation()
            R.id.equal -> showResult()
        }
    }

    private fun addNumber(value: String) {
        equation += value
        binding.equation.text = equation
    }

    private fun addOperator(operator: String) {
        if (equation.isNotEmpty() && !isLastCharacterOperator()) {
            equation += operator
            binding.equation.text = equation
        }
    }

    private fun addDot() {
        if (equation.isNotEmpty() && !isLastCharacterOperator() && equation.last() != '.') {
            equation += "."
            binding.equation.text = equation
        }
    }

    private fun removeLastCharacter() {
        if (equation.isNotEmpty()) {
            equation = equation.substring(0,equation.length-1)
            binding.equation.text = equation
        }
    }

    private fun clearEquation() {
        equation = ""
        binding.equation.text = equation
        binding.result.text = equation
    }

    private fun showResult() {
        binding.result.text = equation
        if (equation.isNotEmpty()) {
            val result = evaluate(equation)
            binding.result.text = result
        }
    }

    private fun isLastCharacterOperator(): Boolean {
        val lastChar = equation[equation.length-1]
        return lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/'
    }

    private fun evaluate(input: String): String {
        try {
            val result = Expression(input)
            return result.calculate().toString()
        } catch (e: Exception) {
            return "Error: ${e.message}"
        }
    }
}
