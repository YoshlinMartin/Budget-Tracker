package com.example.budgettracker.models

data class Expense(
    var expenseId: String = "",
    var title: String = "",
    var amount: Double = 0.0,
    var category: String = "",
    var date: String = "",
    var description: String = "",

)
