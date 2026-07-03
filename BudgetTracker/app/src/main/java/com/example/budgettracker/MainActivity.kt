package com.example.budgettracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btnAddExpense: Button
    private lateinit var btnViewExpenses: Button
    private lateinit var btnCharts:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnAddExpense=findViewById(R.id.btnAddExpense)
        btnViewExpenses=findViewById(R.id.btnViewExpenses)
        btnCharts=findViewById(R.id.btnCharts)

        btnAddExpense.setOnClickListener {
            val intent= Intent(this, AddExpense::class.java)
            startActivity(intent)
        }
        btnViewExpenses.setOnClickListener {
            val intent = Intent(this, ViewExpenses::class.java)
            startActivity(intent)
        }

        btnCharts.setOnClickListener {
            val intent = Intent(this, ChartsActivity::class.java)
            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}