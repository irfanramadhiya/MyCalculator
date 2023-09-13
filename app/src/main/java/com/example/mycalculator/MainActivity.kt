package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var dotUsed: Boolean = false
    var lastNumeric: Boolean = false
    var isEmpty: Boolean = true
    var operatorUsed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric = true
        isEmpty = false
    }

    fun onClear(view: View){
        tvInput?.text = ""
        dotUsed = false
        lastNumeric = false
        isEmpty = true
        operatorUsed = false
    }

    fun onDecimalPoint(view: View){
        if(!dotUsed && lastNumeric){
            tvInput?.append((view as Button).text)
            dotUsed = true
            lastNumeric = false
            isEmpty = false
        }
    }

    fun onOperator(view: View){
        if(lastNumeric && !operatorUsed) {
            tvInput?.append((view as Button).text)
            dotUsed = false
            lastNumeric = false
            operatorUsed = true
        }
        else if(isEmpty && (view as Button).text == "-"){ // if want to add - value ex: -6
            tvInput?.append(view.text)
            lastNumeric = false
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var containPrefix = false

            try {
                if(tvValue.startsWith("-")){
                    containPrefix = true
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains("-")){
                    subtraction(tvValue, containPrefix)
                }
                else if(tvValue.contains("+")){
                    addition(tvValue, containPrefix)
                }
                else if(tvValue.contains("*")){
                    multiplication(tvValue, containPrefix)
                }
                else if(tvValue.contains("/")){
                    division(tvValue, containPrefix)
                }
                operatorUsed = false
                dotUsed = true
            } catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun addition(tvValue: String, containPrefix: Boolean){
        val splitValue = tvValue.split("+")
        var one = splitValue[0]
        var two = splitValue[1]
        if(containPrefix){
            one = "-$one"
        }
        tvInput?.text = (one.toDouble() + two.toDouble()).toString()
    }

    private fun subtraction(tvValue: String, containPrefix: Boolean){
        val splitValue = tvValue.split("-")
        var one = splitValue[0]
        var two = splitValue[1]
        if(containPrefix){
            one = "-$one"
        }
        tvInput?.text = (one.toDouble() - two.toDouble()).toString()
    }

    private fun multiplication(tvValue: String, containPrefix: Boolean){
        val splitValue = tvValue.split("*")
        var one = splitValue[0]
        var two = splitValue[1]
        if(containPrefix){
            one = "-$one"
        }
        tvInput?.text = (one.toDouble() * two.toDouble()).toString()
    }

    private fun division(tvValue: String, containPrefix: Boolean){
        val splitValue = tvValue.split("/")
        var one = splitValue[0]
        var two = splitValue[1]
        if(containPrefix){
            one = "-$one"
        }
        tvInput?.text = (one.toDouble() / two.toDouble()).toString()
    }
}