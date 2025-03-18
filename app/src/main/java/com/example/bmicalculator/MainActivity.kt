package com.example.bmicalculator

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.android.material.slider.Slider
import kotlin.math.pow
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    //Weight Variables
    lateinit var removeWeightButton: Button
    lateinit var addWeightButton: Button
    lateinit var weightTextView: TextView
    var weight = 70.0f

    //Height Variables
    lateinit var heightSlider: Slider
    lateinit var heightTextView: TextView
    var height = 170.0f

    //BMI Variables
    lateinit var calculateButton: Button
    lateinit var bmiTextView: TextView
    lateinit var bmiCardView: CardView
    lateinit var bmiIndexTextView: TextView

    //Warning Variables
    lateinit var warningTextView: TextView
    lateinit var warningCardView: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        removeWeightButton = findViewById(R.id.removeWeightButton)
        addWeightButton = findViewById(R.id.addWeightButton)
        weightTextView = findViewById(R.id.weightTextView)

        heightSlider = findViewById(R.id.heightSlider)
        heightTextView = findViewById(R.id.heightTextView)

        calculateButton = findViewById(R.id.calculateButton)
        bmiTextView = findViewById(R.id.bmiTextView)
        bmiCardView = findViewById(R.id.bmiCardView)
        bmiIndexTextView = findViewById(R.id.bmiIndexTextView)

        warningTextView = findViewById(R.id.warningTextView)
        warningCardView = findViewById(R.id.warningCardView)

        setWeight()
        setHeight()
        calculateBMI()

    }

    fun setWeight(){
        removeWeightButton.setOnClickListener {
            weight --
            if (weight < 30) {
                weight = 30f
            }
            weightTextView.text = "${weight.toInt()} kg"
        }

        addWeightButton.setOnClickListener {
            weight ++
            if (weight > 300) {
                weight = 300f
            }
            weightTextView.text = "${weight.toInt()} kg"
        }
    }

    fun setHeight(){
        heightSlider.addOnChangeListener { slider, value, fromUser ->
            height = value
            heightTextView.text = "${value.toInt()} cm"
        }
    }

    fun calculateBMI(){

        bmiCardView.isVisible = false
        warningCardView.isVisible = false

        calculateButton.setOnClickListener {
            val bmi = weight / (height / 100).pow(2)
            bmiTextView.text = String.format("%.2f", bmi)
            when{
                bmi < 18.5f -> {
                    bmiIndexTextView.text = "Bajo peso"
                    bmiIndexTextView.setTextColor(Color.parseColor("#ff0000"))
                    bmiTextView.setTextColor(Color.parseColor("#ff0000"))
                    warningTextView.text = "Tu IMC indica bajo peso." +
                            " Aumenta calorías con alimentos nutritivos y " +
                            "consulta a un especialista si es necesario."
                }
                bmi >= 18.5f && bmi <= 24.9 -> {
                    bmiIndexTextView.text = "Normal"
                    bmiIndexTextView.setTextColor(Color.parseColor("#008000"))
                    bmiTextView.setTextColor(Color.parseColor("#008000"))
                    warningTextView.text = "¡Tu IMC está en el rango saludable! " +
                            "Mantén una dieta equilibrada, haz ejercicio y " +
                            "prioriza el bienestar."
                }
                bmi >= 25.0f && bmi <= 29.9 -> {
                    bmiIndexTextView.text = "Sobrepeso"
                    bmiIndexTextView.setTextColor(Color.parseColor("#BA8E23"))
                    bmiTextView.setTextColor(Color.parseColor("#BA8E23"))
                    warningTextView.text = "Tu IMC indica sobrepeso. " +
                            "Mejora tu alimentación, aumenta la actividad física y " +
                            "cuida tus hábitos diarios."
                }
                bmi >= 30.0f -> {
                    bmiIndexTextView.text = "Obesidad"
                    bmiIndexTextView.setTextColor(Color.parseColor("#ff0000"))
                    bmiTextView.setTextColor(Color.parseColor("#ff0000"))
                    warningTextView.text = "Tu IMC indica obesidad. " +
                            "Consulta a un experto, adopta cambios sostenibles y " +
                            "evita dietas extremas."
                }
            }
            bmiCardView.isVisible = true
            warningCardView.isVisible = true
            //imcTextView.text = "$imc"
        }
    }
}