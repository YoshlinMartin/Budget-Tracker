package com.example.budgettracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.budgettracker.R
import com.example.budgettracker.models.Expense

class ExpenseAdapter(private val expenseList: List<Expense>) :
    RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView = view.findViewById(R.id.txtTitle)
        val amount: TextView = view.findViewById(R.id.txtAmount)
        val category: TextView = view.findViewById(R.id.txtCategory)
        val date: TextView = view.findViewById(R.id.txtDate)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_expense_item_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val expense = expenseList[position]

        holder.title.text = expense.title
        holder.amount.text = "R ${expense.amount}"
        holder.category.text = expense.category
        holder.date.text = expense.date


    }

    override fun getItemCount(): Int {
        return expenseList.size
    }
}