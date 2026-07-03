package com.example.budgettracker

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ExpenseItemLayout : AppCompatActivity() {


    private lateinit var txtTitle: EditText
    private lateinit var txtAmount: EditText
    private lateinit var txtCategory: EditText
    private lateinit var txtDate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_expense_item_layout)


        txtTitle=findViewById(R.id.txtTitle)
        txtAmount=findViewById(R.id.txtAmount)
        txtCategory=findViewById(R.id.txtCategory)
        txtDate=findViewById(R.id.txtDate)





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}