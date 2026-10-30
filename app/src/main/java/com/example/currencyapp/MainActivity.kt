package com.example.currencyapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val currencies = arrayOf("RUB", "USD", "EUR", "CNY", "GBP", "JPY")
    private val ratesToRub = mapOf(
        "RUB" to 1.0, "USD" to 0.011, "EUR" to 0.010, "CNY" to 0.079, "GBP" to 0.0086, "JPY" to 1.65
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Проверка, что id="main" существует в activity_main.xml
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etAmount = findViewById<EditText>(R.id.etAmount)
        val spinnerFrom = findViewById<Spinner>(R.id.spinnerFrom)
        val spinnerTo = findViewById<Spinner>(R.id.spinnerTo)
        val btnConvert = findViewById<Button>(R.id.btnConvert)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val btnAuthors = findViewById<Button>(R.id.btnAuthors) // Нашли кнопку авторов

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter
        spinnerFrom.setSelection(1)
        spinnerTo.setSelection(0)

        btnConvert.setOnClickListener {
            val amountStr = etAmount.text.toString()

            // Защита от пустого поля или ввода некорректных данных
            if (amountStr.isEmpty()) {
                Toast.makeText(this, "Введите сумму!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val amount = amountStr.toDouble()
                val fromCurrency = spinnerFrom.selectedItem.toString()
                val toCurrency = spinnerTo.selectedItem.toString()

                val amountInRub = amount / ratesToRub[fromCurrency]!!
                val result = amountInRub * ratesToRub[toCurrency]!!

                tvResult.text = String.format("Результат: %.2f %s", result, toCurrency)
            } catch (e: Exception) {
                Toast.makeText(this, "Ошибка ввода числа", Toast.LENGTH_SHORT).show()
            }
        }

        // Пока кнопка авторов ничего не делает, но она есть
        btnAuthors.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }
}