package com.example.budgettracker

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.budgettracker.adapter.ExpenseAdapter
import com.example.budgettracker.models.Expense
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewExpenses : AppCompatActivity() {

    private lateinit var btnFilter: Button
    private lateinit var recyclerView: RecyclerView
    private  lateinit var expenseList: ArrayList<Expense>

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_expenses)

        btnFilter=findViewById(R.id.btnFilter)
        recyclerView=findViewById(R.id.recyclerView)

        expenseList = arrayListOf()

        loadExpenses()
    }

    private fun loadExpenses() {

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser == null) {
            finish() // user not logged in
            return
        }

        val userId = currentUser.uid

        val database = FirebaseDatabase.getInstance().reference
            .child("Expenses")
            .child(userId)

        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                expenseList.clear()

                for (data in snapshot.children) {

                    val expense = data.getValue(Expense::class.java)

                    if (expense != null) {
                        expenseList.add(expense)
                    }
                }

                recyclerView.adapter = ExpenseAdapter(expenseList)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}