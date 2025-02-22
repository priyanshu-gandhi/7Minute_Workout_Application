package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {

    private var binding: ActivityBmiBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.bmiToolbar)
        supportActionBar?.title = "BMI Calculator"

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.bmiToolbar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding?.bmiWeight?.visibility =View.INVISIBLE
        binding?.bmiWeightPounds?.visibility =View.VISIBLE
        binding?.editHeight?.visibility = View.INVISIBLE
        binding?.editFeet?.visibility = View.VISIBLE
        binding?.editInch?.visibility = View.VISIBLE

       binding?.rvMetricUnits?.setOnClickListener {
           binding?.bmiWeight?.visibility =View.VISIBLE
           binding?.bmiWeightPounds?.visibility =View.INVISIBLE
           binding?.editHeight?.visibility = View.VISIBLE
           binding?.editFeet?.visibility = View.GONE
           binding?.editInch?.visibility = View.GONE
       }
        binding?.rvUsUnits?.setOnClickListener {
            binding?.bmiWeight?.visibility =View.INVISIBLE
            binding?.bmiWeightPounds?.visibility =View.VISIBLE
            binding?.editHeight?.visibility = View.INVISIBLE
            binding?.editFeet?.visibility = View.VISIBLE
            binding?.editInch?.visibility = View.VISIBLE
        }

        binding?.bmiBtn?.setOnClickListener {
            if (validUnits()) {

                var bmi : Double?= null
                if(binding?.rvMetricUnits?.isChecked == true) {


                    var heightValue = binding?.editHeight?.text.toString()
                        .toDouble() / 100    // we need height in metre for calculations
                    var weightValue = binding?.editWeight?.text.toString().toDouble()

                     bmi = weightValue / (heightValue * heightValue)
                }
                else
                {
                    var weightValue = binding?.editWeight?.text.toString().toDouble()

                    var heightValue = (binding?.editFeet?.text.toString().toDouble()*12)+(binding?.editInch?.text.toString().toDouble())

                    bmi= 703* (weightValue/ (heightValue * heightValue))
                }

                displayBmiResult(bmi)

            }
        }


    }

    private fun displayBmiResult(bmi: Double) {

        val bmiLabel: String
        val bmiDescription: String

        when {
            bmi < 18.5 -> {
                bmiLabel = "Underweight"
                bmiDescription =
                    "You are underweight. Consider eating a balanced diet and gaining some weight."
            }

            bmi in 18.5..24.9 -> {
                bmiLabel = "Normal weight"
                bmiDescription = "You have a normal body weight. Great job!"
            }

            bmi in 25.0..29.9 -> {
                bmiLabel = "Overweight"
                bmiDescription =
                    "You are overweight. Consider adopting a healthier diet and regular exercise."
            }

            bmi in 30.0..34.9 -> {
                bmiLabel = "Obese (Class 1)"
                bmiDescription =
                    "You are in the obese category (Class 1). It's important to adopt a healthier lifestyle."
            }

            bmi in 35.0..39.9 -> {
                bmiLabel = "Obese (Class 2)"
                bmiDescription =
                    "You are in the obese category (Class 2). Consider consulting with a healthcare provider for guidance."
            }

            bmi >= 40.0 -> {
                bmiLabel = "Morbidly Obese (Class 3)"
                bmiDescription =
                    "You are in the morbidly obese category (Class 3). Seek medical advice to address your health risks."
            }

            else -> {
                bmiLabel = "Unknown"
                bmiDescription = "BMI could not be determined. Please check the input values."
            }
        }

        var bmiRoundOff = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.bmiLl?.visibility = View.VISIBLE
        binding?.bmiValue?.text = bmiRoundOff
        binding?.bmiStatus?.text = bmiLabel
        binding?.bmiDesc?.text = bmiDescription

    }

    private fun validUnits(): Boolean {
        var isValid = true

        var isvalidHeight = binding?.editHeight?.text.toString()
        var isvalidWeight = binding?.editWeight?.text.toString()
        var isvalidWeight_pounds =binding?.editWeightPounds?.text.toString()
        var isvalidFoot = binding?.editFeet?.text.toString()
        var isvalidInch = binding?.editInch?.text.toString()

        if (binding?.rvMetricUnits?.isChecked == true) {

            if (isvalidHeight.isNotEmpty() and isvalidWeight.isNotEmpty()) {
                return isValid
            } else {
                isValid = false
                Toast.makeText(this, "Please enter some value in both fields", Toast.LENGTH_LONG).show()
                return isValid
            }

        }

        if (binding?.rvUsUnits?.isChecked == true) {
            Log.d("cal","${isvalidWeight_pounds}, ${isvalidFoot}, ${isvalidInch}")
            if (isvalidWeight_pounds.isNotEmpty() and isvalidFoot.isNotEmpty() and isvalidInch.isNotEmpty()) {
                return isValid
            } else {
                isValid = false
                Toast.makeText(this, "Please enter some value in all fields", Toast.LENGTH_LONG).show()
                return isValid
            }

        }
        return isValid
    }

        override fun onDestroy() {
            super.onDestroy()
            binding = null
        }

}