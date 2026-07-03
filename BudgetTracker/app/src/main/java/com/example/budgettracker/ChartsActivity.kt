package com.example.budgettracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.budgettracker.models.Expense
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChartsActivity : AppCompatActivity() {

    private lateinit var pieChart: PieChart
    private lateinit var barChart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_charts)

        pieChart = findViewById(R.id.pieChart)
        barChart = findViewById(R.id.barChart)

        loadChartData()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loadChartData() {

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser == null) {
            finish() // or redirect to login screen
            return
        }

        val userId = currentUser.uid

        val ref = FirebaseDatabase.getInstance().reference
            .child("Expenses")
            .child(userId)

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                val categoryTotals = HashMap<String, Float>()

                for (data in snapshot.children) {

                    val expense = data.getValue(Expense::class.java)

                    if (expense != null) {

                        val current = categoryTotals[expense.category] ?: 0f
                        categoryTotals[expense.category] =
                            current + expense.amount.toFloat()
                    }
                }

                setupPieChart(categoryTotals)
                setupBarChart(categoryTotals)
            }

            override fun onCancelled(error: DatabaseError) {
                // optional error handling
            }
        })
    }

    private fun setupPieChart(dataMap: HashMap<String, Float>) {

        val entries = ArrayList<PieEntry>()

        for ((category, amount) in dataMap) {
            entries.add(PieEntry(amount, category))
        }

        val dataSet = PieDataSet(entries, "Expenses")
        val pieData = PieData(dataSet)

        pieChart.data = pieData
        pieChart.invalidate()
    }

    private fun setupBarChart(dataMap: HashMap<String, Float>) {

        val entries = ArrayList<BarEntry>()
        var index = 0f

        for ((_, amount) in dataMap) {
            entries.add(BarEntry(index, amount))
            index++
        }

        val dataSet = BarDataSet(entries, "Expenses")
        val data = BarData(dataSet)

        barChart.data = data
        barChart.invalidate()
    }
}